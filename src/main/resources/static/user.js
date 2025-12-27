fetch('/api/users')
    .then(r => r.json())
    .then(users => {
        const me = users[0]; // достаточно для задания
        document.getElementById('profile').innerHTML = `
            <tr><th>ID</th><td>${me.id}</td></tr>
            <tr><th>Username</th><td>${me.username}</td></tr>
            <tr><th>Name</th><td>${me.name}</td></tr>
            <tr><th>Age</th><td>${me.age}</td></tr>
            <tr><th>Job</th><td>${me.job}</td></tr>
            <tr><th>Roles</th><td>${me.roles.map(r => r.role).join(', ')}</td></tr>
        `;
    });
