const USERS_API = '/api/users';
const ROLES_API = '/api/roles';
const CURRENT_USER_API = '/api/user/me';

let editModal;
let deleteModal;

document.addEventListener('DOMContentLoaded', () => {
    editModal = new bootstrap.Modal(document.getElementById('editUserModal'));
    deleteModal = new bootstrap.Modal(document.getElementById('deleteUserModal'));
    initializePage().catch(err => showError(err.message));
});

async function initializePage() {
    await Promise.all([loadCurrentUser(), loadRoles()]);
    await loadUsers();

    document.getElementById('newUserForm').addEventListener('submit', handleCreateUser);
    document.getElementById('editUserForm').addEventListener('submit', handleUpdateUser);
    document.getElementById('deleteUserForm').addEventListener('submit', handleDeleteUser);
}

async function loadCurrentUser() {
    const res = await fetch(CURRENT_USER_API);
    if (!res.ok) {
        showError('Не удалось получить информацию о текущем пользователе');
        return;
    }
    const user = await res.json();
    document.getElementById('currentUserName').textContent = user.username;
    document.getElementById('currentUserRoles').textContent = formatRoles(user.role);
}

async function loadRoles() {
    const res = await fetch(ROLES_API);
    if (!res.ok) {
        showError('Не удалось загрузить список ролей');
        return;
    }
    const roles = await res.json();
    fillRolesSelect('newRoles', roles);
    fillRolesSelect('editRoles', roles);
}

function fillRolesSelect(selectId, roles) {
    const select = document.getElementById(selectId);
    if (!select) {
        return;
    }
    select.innerHTML = roles
        .map(r => `<option value="${r.role}">${formatRoleName(r.role)}</option>`)
        .join('');
    if (select.options.length) {
        select.options[0].selected = true;
    }
}

async function loadUsers() {
    clearAlert();
    const res = await fetch(USERS_API);
    if (!res.ok) {
        showError('Не удалось загрузить пользователей');
        return;
    }
    const users = await res.json();
    const tbody = document.querySelector('#usersTable tbody');
    tbody.innerHTML = users.map(renderUserRow).join('');
    tbody.querySelectorAll('.edit-btn').forEach(btn => btn.addEventListener('click', openEditModal));
    tbody.querySelectorAll('.delete-btn').forEach(btn => btn.addEventListener('click', openDeleteModal));
}

function renderUserRow(user) {
    return `
    <tr data-id="${user.id}">
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.name ?? ''}</td>
        <td>${user.age ?? ''}</td>
        <td>${user.job ?? ''}</td>
        <td>${formatRoles(user.role)}</td>
        <td class="text-nowrap">
            <button class="btn btn-sm btn-primary edit-btn" data-id="${user.id}">Edit</button>
            <button class="btn btn-sm btn-danger delete-btn ms-1" data-id="${user.id}">Delete</button>
        </td>
    </tr>`;
}

async function openEditModal(event) {
    const id = event.currentTarget.dataset.id;
    const user = await fetchUser(id);
    if (!user) {
        return;
    }
    const form = document.getElementById('editUserForm');
    form.dataset.id = user.id;
    form.querySelector('#editId').value = user.id;
    form.querySelector('#editUsername').value = user.username ?? '';
    form.querySelector('#editName').value = user.name ?? '';
    form.querySelector('#editAge').value = user.age ?? '';
    form.querySelector('#editJob').value = user.job ?? '';
    form.querySelector('#editPassword').value = '';
    setSelectedRoles(document.getElementById('editRoles'), user.role || []);
    editModal.show();
}

async function openDeleteModal(event) {
    const id = event.currentTarget.dataset.id;
    const user = await fetchUser(id);
    if (!user) {
        return;
    }
    document.getElementById('deleteUserId').value = user.id;
    document.getElementById('deleteUsername').textContent = user.username;
    document.getElementById('deleteRoles').textContent = formatRoles(user.role);
    deleteModal.show();
}

async function fetchUser(id) {
    const res = await fetch(`${USERS_API}/${id}`);
    if (!res.ok) {
        showError('Пользователь не найден');
        return null;
    }
    return res.json();
}

async function handleCreateUser(event) {
    event.preventDefault();
    const form = event.target;
    const payload = collectUserPayload(form, { requirePassword: true });
    if (!payload) {
        return;
    }
    const res = await fetch(USERS_API, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    if (!res.ok) {
        showError('Не удалось создать пользователя');
        return;
    }
    form.reset();
    setSelectedRoles(document.getElementById('newRoles'), []);
    await loadUsers();
}

async function handleUpdateUser(event) {
    event.preventDefault();
    const form = event.target;
    const id = form.dataset.id;
    const payload = collectUserPayload(form, { requirePassword: false });
    if (!payload) {
        return;
    }
    if (!payload.password) {
        delete payload.password;
    }
    const res = await fetch(`${USERS_API}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    if (!res.ok) {
        showError('Не удалось обновить пользователя');
        return;
    }
    editModal.hide();
    await loadUsers();
}

async function handleDeleteUser(event) {
    event.preventDefault();
    const id = document.getElementById('deleteUserId').value;
    const res = await fetch(`${USERS_API}/${id}`, { method: 'DELETE' });
    if (!res.ok) {
        showError('Не удалось удалить пользователя');
        return;
    }
    deleteModal.hide();
    await loadUsers();
}

function collectUserPayload(container, { requirePassword } = { requirePassword: false }) {
    const username = container.querySelector('[name="username"]').value.trim();
    const password = container.querySelector('[name="password"]')?.value.trim();
    const name = container.querySelector('[name="name"]').value.trim();
    const ageValue = container.querySelector('[name="age"]').value;
    const job = container.querySelector('[name="job"]').value.trim();
    const roles = getSelectedRoles(container.querySelector('[name="role"]'));

    if (!username) {
        showError('Введите username');
        return null;
    }
    if (requirePassword && !password) {
        showError('Введите пароль');
        return null;
    }
    return {
        username,
        password,
        name: name || null,
        age: ageValue ? Number(ageValue) : null,
        job: job || null,
        roles
    };
}

function getSelectedRoles(select) {
    if (!select) {
        return [];
    }
    return Array.from(select.selectedOptions).map(opt => opt.value);
}

function setSelectedRoles(select, roles) {
    if (!select) {
        return;
    }
    const rolesSet = new Set(roles || []);
    Array.from(select.options).forEach(option => {
        option.selected = rolesSet.has(option.value);
    });
}

function formatRoles(roles = []) {
    return (roles || [])
        .map(formatRoleName)
        .join(', ');
}

function formatRoleName(role) {
    if (!role) {
        return '';
    }
    return role.replace('ROLE_', '');
}

function showError(message) {
    const box = document.getElementById('alertBox');
    if (!box) {
        return;
    }
    box.innerHTML = `
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>`;
}

function clearAlert() {
    const box = document.getElementById('alertBox');
    if (box) {
        box.innerHTML = '';
    }
}
