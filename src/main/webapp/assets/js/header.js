function initUpdateAddress(event,parent, formElement) {
    event.preventDefault();
    const parentElement = event.currentTarget.closest(parent);
    let id = parentElement.querySelector('.id').innerText;
    let receiver = parentElement.querySelector('.receiver').innerText;
    let phone = parentElement.querySelector('.phone').innerText;
    let street = parentElement.querySelector('.street').innerText;
    let village = parentElement.querySelector('.village').innerText;
    let district = parentElement.querySelector('.district').innerText;
    let province = parentElement.querySelector('.province').innerText;
    const form = document.querySelector(formElement);
    const inputs = form.querySelectorAll('input');
    inputs.forEach(input => {
        if(input.name == "id") input.value = id;
        else if(input.name == "receiver") input.value = receiver;
        else if(input.name == "phone") input.value = phone;
        else if(input.name == "street") input.value = street;
        else if(input.name == "village") input.value = village;
        else if(input.name == "district") input.value = district;
        else if(input.name == "province") input.value = province;
    });
    form.querySelector('.edit-title').innerText = "Cập nhật địa chỉ";

}

function cancelEdit(element) {
    const form = document.querySelector(element);
    const inputs = form.querySelectorAll('input');
    inputs.forEach(input => {
        input.value = '';
    });
    form.querySelector('.edit-title').innerText = "Thêm địa chỉ";

}

function initDeleteAddress(event,parent,modal,confirmModal) {
    const parentElement = event.currentTarget.closest(parent);
    let id = parentElement.querySelector('.id').innerText;
    let receiver = parentElement.querySelector('.receiver').innerText;
    let phone = parentElement.querySelector('.phone').innerText;
    let address = parentElement.querySelector('.address').innerText;

    const modalElement = parentElement.closest(modal);
    const confirmElement = modalElement.querySelector(confirmModal);
    confirmElement.querySelector('.id').innerText = id;
    confirmElement.querySelector('.receiver').innerText = receiver;
    confirmElement.querySelector('.phone').innerText = phone;
    confirmElement.querySelector('.address').innerText = address;
    confirmElement.classList.add('active');
}

function cancelDelete(event,element) {
    event.currentTarget.closest(element).classList.remove('active');
}

function initUpdateInfo(event,parent) {
    const form = event.currentTarget.closest(parent);
    const inputs = form.querySelectorAll('input');
    inputs.forEach(input => {
        input.removeAttribute('readonly');
    });
    form.querySelector('.update-action').style.display = 'none';
    form.querySelector('.edit-title').style.display = 'block';
    form.querySelector('.btn-edit-container').style.display = 'flex';
}

function cancelEditInfo(event,parent) {
    const form = event.currentTarget.closest(parent);
    const inputs = form.querySelectorAll('input');
    inputs.forEach(input => {
        input.setAttribute('readonly',true);
    });
    form.querySelector('.update-action').style.display = 'flex';
    form.querySelector('.edit-title').style.display = 'none';
    form.querySelector('.btn-edit-container').style.display = 'none';

//     them ham goi ajax de lay lai gia tri ban dau
}

function initChangePwd(event,pwdForm) {
    event.preventDefault();
    document.querySelector(pwdForm).classList.add('active');
}

function cancelChangePwd(pwdForm) {
    document.querySelector(pwdForm).classList.remove('active');
}

function manageAddress(event) {
    event.preventDefault();
    document.querySelector('.address-modal').classList.add('active');
    getAddressList();
}

function manageProfile(event) {
    event.preventDefault();
    document.querySelector('.info-modal').classList.add('active');
}

function openHeaderModal(modal) {
    const modalElement = document.querySelector(modal);
    modalElement.classList.add('active');

}

function getAddressList() {
    $.ajax({
        type: "GET",
        url: event.currentTarget.href,
        success: function(data) {
            console.log($('#address-container'));
            $('#address-container').html(data);
        },
        error: function(error) {
            console.error("Error during querying address: ", error);
        }
    });
}