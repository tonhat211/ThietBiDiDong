function radioElements(element) {
    const elements = document.querySelectorAll(element);
    elements.forEach(function(e) {
        e.addEventListener('click', function() {
            const parent = this.closest('.group');
            if(!parent) return;
            const subs = parent.querySelectorAll(element);
            subs.forEach(function(item) {
                item.classList.remove('active');
            });
            this.classList.add('active');
        });
    });
}

function changeToProductUrl() {
    const basePath = window.location.pathname.split("/").slice(0, 2).join("/");

    const newPath = '/product'; // Phần path mới bạn muốn thay đổi
    window.history.pushState(null, '', basePath + newPath);

}
