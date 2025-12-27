document.addEventListener('DOMContentLoaded', () => {
    loadProfile().catch(() => renderError());
});

async function loadProfile() {
    const res = await fetch('/api/user/me');
    if (!res.ok) {
        renderError();
        return;
    }
    const me = await res.json();
    document.getElementById('currentUserName').textContent = me.username;
    document.getElementById('currentUserRoles').textContent = formatRoles(me.role);
    document.getElementById('profile').innerHTML = `
        <tr><th>ID</th><td>${me.id}</td></tr>
        <tr><th>Username</th><td>${me.username}</td></tr>
        <tr><th>Name</th><td>${me.name ?? ''}</td></tr>
        <tr><th>Age</th><td>${me.age ?? ''}</td></tr>
        <tr><th>Job</th><td>${me.job ?? ''}</td></tr>
        <tr><th>Roles</th><td>${formatRoles(me.role)}</td></tr>
    `;
}

function formatRoles(roles = []) {
    return (roles || []).map(r => r.replace('ROLE_', '')).join(', ');
}

function renderError() {
    document.getElementById('profile').innerHTML =
        '<tr><td colspan="2" class="text-danger">Не удалось загрузить профиль</td></tr>';
}
