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

function removeModal(modal) {
    console.log("remove modal");
    const parent = document.querySelector(modal);
    const children = parent.children;
    if(!parent || !children) return;
    Array.from(children).forEach(function(e) {
        if(!e.classList.contains('active')) return;
        e.classList.remove('active');
    });
}

function openModal(modal) {
    console.log("open modal");
    const parent = document.querySelector(modal);
    console.log(parent);
    const children = parent.children;
    console.log(children);
    if(!parent || !children) return;
    Array.from(children).forEach(function(e) {
        if(e.classList.contains('not-active')) return;
        e.classList.add('active');
    });
}