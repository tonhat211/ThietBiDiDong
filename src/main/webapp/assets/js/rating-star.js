function activeRating(container) {
    const parent = document.querySelector(container);
    if (!parent) {
        console.log('Khong tim thay the cha ' + container);
        return;
    }
    const stars = parent.querySelectorAll('.rating .star');
    if (!parent) {
        console.log('Khong tim thay the stars .rating .star');
        return;
    }
    stars.forEach((star) => {
        star.addEventListener('click', function() {
            const ratingValue = this.getAttribute('data-value');
            for(let i=0;i<stars.length; i++) {
                if(stars[i].getAttribute('data-value') <= ratingValue) {
                    stars[i].innerHTML = `<i class="bi bi-star-fill"></i>`;
                } else {
                    stars[i].innerHTML = `<i class="bi bi-star"></i>`;
                }
            }
        });
    });
}

function initRating(container,rate) {
    const parent = document.querySelector(container);
    if (!parent) {
        console.log('Khong tim thay the cha ' + container);
        return;
    }
    const stars = parent.querySelectorAll('.rating .star');
    if (!parent) {
        console.log('Khong tim thay the stars .rating .star');
        return;
    }
    // vd: 3.5 - 3 = 0.5 -> tu 1 den 3 la full, thu 4 la half, thu 5 la rong
    let tempRate = parseInt(rate);
    if(rate - tempRate > 0) { // rate la so thap phan
        for(let i=0; i<stars.length;i++) {
            if(stars[i].getAttribute('data-value') <= tempRate) {  // <= 3
                stars[i].innerHTML = `<i class="bi bi-star-fill"></i>`;
            } else if (stars[i].getAttribute('data-value') == (tempRate+1)) { // == 4
                stars[i].innerHTML = `<i class="bi bi-star-half">`;
            } else {
                stars[i].innerHTML = `<i class="bi bi-star"></i>`;
            }
        }
    } else { // rate la so nguyen vd rate = 3
        for(let i=0; i<stars.length;i++) {
            if(stars[i].getAttribute('data-value') <= rate) {  // <= 3
                stars[i].innerHTML = `<i class="bi bi-star-fill"></i>`;
            } else {
                stars[i].innerHTML = `<i class="bi bi-star"></i>`;
            }
        }
    }
}
