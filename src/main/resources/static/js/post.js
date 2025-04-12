document.querySelector('#addNewUser').addEventListener("click", async function add(evt) {
    evt.preventDefault();
    let addForm = document.querySelector('#formAddUser');

    // Добавляем проверку валидации
    if (!addForm.checkValidity()) {
        addForm.classList.add('was-validated');
        return;
    }

    let username = addForm.addUsername.value;
    let age = addForm.addAge.value;
    let email = addForm.addEmail.value;
    let password = addForm.addPassword.value;

    let getRole = () => {
        let array = [];
        let role = document.querySelector('#Inputrole'); // или #editRole для редактирования
        let isValid = false;

        for (let i = 0; i < role.length; i++) {
            if (role[i].selected) {
                array.push(roleList[i]);
                isValid = true;
            }
        }

        if (!isValid) {
            role.setCustomValidity("Please select at least one role");
            role.reportValidity();
            throw new Error("Role not selected");
        } else {
            role.setCustomValidity("");
        }

        return array;
    }

    let user = {
        username: username,
        age: age,
        email: email,
        password: password,
        roles: getRole()
    }

    let addUser = await fetch('api/admin',
        {
            method: "POST",
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json',
            },
            body: JSON.stringify(user)
        });
    if (addUser.ok) {
        // Сбрасываем форму и убираем сообщения валидации
        addForm.reset();
        addForm.classList.remove('was-validated');

        // Переключаемся на вкладку со списком пользователей
        document.querySelector('#alluser-tab').click();

        // Обновляем список пользователей
        await getAllUsers();
    } else {
        // Обработка ошибок сервера
        const error = await addUser.json();
        alert(`Error: ${JSON.stringify(error)}`);
    }
});