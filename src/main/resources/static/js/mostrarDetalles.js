    document.getElementById('showUserForm').addEventListener('click', function() {
    document.getElementById('userForm').classList.remove('hidden');
    document.getElementById('detailForm').classList.add('hidden');
});

    document.getElementById('showDetailForm').addEventListener('click', function() {
    document.getElementById('userForm').classList.add('hidden');
    document.getElementById('detailForm').classList.remove('hidden');
});
