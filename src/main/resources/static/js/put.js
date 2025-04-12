async function editUser(id, modal) {
    let myModal = document.querySelector('#modal-body');
    let getOneUser = await fetch('api/admin/' + id);
    let json = getOneUser.json();
    document.getElementById("modalTitle").innerHTML = "Edit user";
    document.getElementById("modal-footer").innerHTML =
        `<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
         <button type="submit" class="btn btn-primary" id="modalBtn">Edit</button>`;
    json.then(json => {
        let htmlEdit = `
    <form id="editUser" class="needs-validation" novalidate>
       <div class="d-flex flex-column align-items-center">
           <div class="mb-3">
               <h6 class="text-dark fw-bold text-center">ID</h6>
               <input type="text" name="editId" style="width: 400px;" class="form-control" id="editId" value="${json.id}" disabled>
           </div>
           <div class="mb-3">
               <h6 class="text-dark fw-bold text-center">Username</h6>
               <input name="editUsername" style="width: 400px;" class="form-control" type="text" id="editUsername" value="${json.username}" required minlength="3" maxlength="30">
               <div class="invalid-feedback">Username must be 3-30 characters long</div>
           </div>
           <div class="mb-3">
               <h6 class="text-dark fw-bold text-center">Password</h6>
               <input name="editPassword" style="width: 400px;" class="form-control" type="password" id="editPassword" placeholder="Enter new password" minlength="5">
               <div class="invalid-feedback">Password must be at least 5 characters</div>
           </div>
           <div class="mb-3">
               <h6 class="text-dark fw-bold text-center">Age</h6>
               <input name="editAge" style="width: 400px;" class="form-control" type="number" id="editAge" value="${json.age}" required min="1" max="120">
               <div class="invalid-feedback">Age must be between 1 and 120</div>
           </div>
           <div class="mb-3">
               <h6 class="text-dark fw-bold text-center">Email</h6>
               <input name="editEmail" style="width: 400px;" class="form-control" type="email" id="editEmail" value="${json.email}" required>
               <div class="invalid-feedback">Please enter a valid email</div>
           </div>
           <div class="mb-3">
               <h6 class="text-dark fw-bold text-center">Role</h6>
               <select style="width: 400px;" id="editRole" class="form-select" multiple required>
                   <option value="ADMIN">ADMIN</option>
                   <option selected="selected" value="USER">USER</option>
               </select>
               <div class="invalid-feedback">Please select at least one role</div>
           </div>
       </div>
   </form>`;
        myModal.innerHTML = htmlEdit;
    })

    document.getElementById("modalBtn").addEventListener('click', async function (evt) {
        evt.preventDefault();
        let editForm = document.querySelector('#editUser');

        // Проверка валидации
        if (!editForm.checkValidity()) {
            editForm.classList.add('was-validated');
            return;
        }

        let id = editForm.editId.value;
        let username = editForm.editUsername.value;
        let age = editForm.editAge.value;
        let email = editForm.editEmail.value;
        let password = editForm.editPassword.value;
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

        let addUser = {
            id: id,
            username: username,
            age: age,
            email: email,
            password: password,
            roles: getRole()
        }

        let update = await fetch('api/admin', {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json',
            },
            body: JSON.stringify(addUser)
        });
        if (update.ok) {
            // Закрываем модальное окно
            modal.modal('hide');

            // Переключаемся на вкладку со списком пользователей
            document.querySelector('#alluser-tab').click();

            // Обновляем список пользователей
            await getAllUsers();
        } else {
            // Обработка ошибок сервера
            const error = await update.json();
            alert(`Error: ${JSON.stringify(error)}`);
        }
    })
}