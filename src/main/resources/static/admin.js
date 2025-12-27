const API = '/api/users';

document.addEventListener('DOMContentLoaded', loadUsers);

function loadUsers() {
    fetch(API)
        .then(r => r.json())
        .then(users => {
            const tbody = document.querySelector('#usersTable tbody');
            tbody.innerHTML = '';
            users.forEach(u => tbody.innerHTML += row(u));
        });
}

function row(u) {
    return `
    <tr>
        <td>${u.id}</td>
        <td>${u.username}</td>
        <td>${u.name}</td>
        <td>${u.age}</td>
        <td>${u.job}</td>
        <td>${u.roles.map(r => r.role).join(', ')}</td>
        <td>
            <button class="btn btn-danger btn-sm" onclick="deleteUser(${u.id})">Delete</button>
        </td>
    </tr>`;
}

function addUser() {
    const user = {
        username: username.value,
        password: password.value,
        name: name.value,
        age: age.value,
        job: job.value,
        roles: roles.value.split(',').map(r => ({ role: r.trim() }))
    };

    fetch(API, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    }).then(() => {
        clearForm();
        loadUsers();
    });
}

function deleteUser(id) {
    fetch(`${API}/${id}`, { method: 'DELETE' })
        .then(loadUsers);
}

function clearForm() {
    ['username','password','name','age','job','roles'].forEach(id => document.getElementById(id).value = '');
}
