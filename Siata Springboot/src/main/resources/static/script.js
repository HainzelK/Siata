let allEvents = [];

async function fetchData() {
    try {
        const response = await fetch("/api/event", {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error("Could not fetch data");
        }

        const data = await response.json();
        console.log("Fetched data:", data);

        allEvents = data.map(event => ({
            eventId: event.eventId,
            eventName: event.eventName,
            eventImg: event.eventImg,
            eventDesc: event.eventDescription,
            eventDate: new Date(event.eventDate), // Convert to Date object
            location: event.location,
            eventTime: event.eventTime
        }));
        console.log("Mapped events:", allEvents);

        renderEvents(allEvents);
    } catch (error) {
        console.log("Error fetching data:", error);
        const cardContainer = document.getElementById("card-container");
        cardContainer.innerHTML = `<p class="error-message">Error fetching data: ${error.message}</p>`;
    }
}

function addCard(event) {
    const cardContainer = document.getElementById("card-container");

    function truncateText(text, limit) {
        if (text.length > limit) {
            return text.substring(0, limit) + '...';
        }
        return text;
    }

    const truncatedDesc = truncateText(event.eventDesc, 100);

    const card = document.createElement("div");
    card.className = "col-md-3 mb-4";

    const now = new Date();
    const isPastEvent = event.eventDate < now;
    const pastEventClass = isPastEvent ? 'past-event' : '';

    // Format date in Bahasa Indonesia
    const formattedDate = event.eventDate.toLocaleDateString('id-ID', {
        weekday: 'long', // "Minggu", "Senin", etc.
        year: 'numeric', // "2021"
        month: 'long', // "Januari", "Februari", etc.
        day: 'numeric' // "31"
    });

    card.innerHTML = `
        <div class="custom-card ${pastEventClass}">
            <div class="img-box">
                <a href="detailEvent.html?id=${encodeURIComponent(event.eventId)}">
                    <img src="data:image/jpeg;base64,${event.eventImg}" class="card-img-top" alt="${event.eventName}">
                </a>
                ${isPastEvent ? '<div class="green-overlay">Selesai</div>' : ''}
            </div>
            <div class="custom-content">
                <h2>${event.eventName}</h2>
                <p class="location-teks">${event.location}</p>
                <p class="location-teks">${formattedDate} ${event.eventTime}</p>
                <p>${truncatedDesc}</p>
                <a href="detailEvent.html?id=${encodeURIComponent(event.eventId)}">Read More</a>
            </div>    
        </div>
    `;

    cardContainer.appendChild(card);
}

function renderEvents(events) {
    const cardContainer = document.getElementById("card-container");
    cardContainer.innerHTML = "";

    if (events.length === 0) {
        cardContainer.innerHTML = "<p>No events found.</p>";
        return;
    }

    const now = new Date();

    // Sort events by date, placing upcoming events first and past events last
    events.sort((a, b) => {
        const dateA = new Date(a.eventDate);
        const dateB = new Date(b.eventDate);
        return (dateA < now ? 1 : 0) - (dateB < now ? 1 : 0) || dateA - dateB;
    });

    events.forEach(event => addCard(event));
}

function filterEvents(filterType) {
    const now = new Date();
    let filteredEvents = [];

    if (filterType === 'all') {
        filteredEvents = allEvents;
    } else if (filterType === 'upcoming') {
        filteredEvents = allEvents.filter(event => event.eventDate > now);
    } else if (filterType === 'past') {
        filteredEvents = allEvents.filter(event => event.eventDate < now);
    }

    renderEvents(filteredEvents);
}

// Call the fetchData function when the page loads
document.addEventListener("DOMContentLoaded", fetchData);

document.getElementById("filter-all").addEventListener("click", () => filterEvents('all'));
document.getElementById("filter-upcoming").addEventListener("click", () => filterEvents('upcoming'));
document.getElementById("filter-past").addEventListener("click", () => filterEvents('past'));

// seles script utk list event


async function login(event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ email, password }),
        });

        if (!response.ok) {
            const errorData = await response.json();
            console.log("Error data:", errorData);
            throw new Error(errorData.message);
        }

        const data = await response.json();
        console.log("Server response:", data);

        if (data.success) {
            console.log("Successful login");
            console.log("Token received:", data.token);
            document.cookie = `token=${data.token}; path=/`;
            window.location.href = "listEvent.html";
        } else {
            console.log("Login failed:", data.message);
            document.getElementById("login-message").innerText = data.message;
        }
    } catch (error) {
        console.log("Error during login:", error);
        document.getElementById("login-message").innerText = error.message || "Error during login. Please try again.";
    }
}

document.addEventListener("DOMContentLoaded", fetchData);
document.getElementById("loginForm").addEventListener("submit", login);

// Registration form handling
document.getElementById("registrationForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    let isValid = true;

    const name = document.getElementById('name');
    const nameError = document.getElementById('nameError');
    const nameExistsError = document.getElementById('nameExistsError');
    if (!name.value.trim()) {
        nameError.style.display = 'block';
        isValid = false;
    } else {
        nameError.style.display = 'none';
    }

    const username = document.getElementById('username');
    const usernameError = document.getElementById('usernameError');
    const usernameExistsError = document.getElementById('usernameExistsError');
    if (!username.value.trim()) {
        usernameError.style.display = 'block';
        isValid = false;
    } else {
        usernameError.style.display = 'none';
    }

    const email = document.getElementById('email');
    const emailError = document.getElementById('emailError');
    if (!email.checkValidity()) {
        emailError.style.display = 'block';
        isValid = false;
    } else {
        emailError.style.display = 'none';
    }

    const phone = document.getElementById('phone');
    const phoneError = document.getElementById('phoneError');
    const phonePattern = /(\+62|62|0)8[1-9][0-9]{8,11}/;
    if (!phone.value.match(phonePattern)) {
        phoneError.style.display = 'block';
        isValid = false;
    } else {
        phoneError.style.display = 'none';
    }

    const password = document.getElementById('password');
    const passwordError = document.getElementById('passwordError');
    if (!password.checkValidity()) {
        passwordError.style.display = 'block';
        isValid = false;
    } else {
        passwordError.style.display = 'none';
    }

    const confirmPassword = document.getElementById('confirmPassword');
    const confirmPasswordError = document.getElementById('confirmPasswordError');
    if (password.value !== confirmPassword.value) {
        confirmPasswordError.style.display = 'block';
        isValid = false;
    } else {
        confirmPasswordError.style.display = 'none';
    }

    const dob = document.getElementById('dob');
    const dobError = document.getElementById('dobError');
    if (!dob.value) {
        dobError.style.display = 'block';
        isValid = false;
    } else {
        dobError.style.display = 'none';
    }

    const genderError = document.getElementById('genderError');
    const selectedGender = document.querySelector('input[name="gender"]:checked');
    if (!selectedGender) {
        genderError.style.display = 'block';
        isValid = false;
    } else {
        genderError.style.display = 'none';
    }

    if (!isValid) {
        return;
    }

    const formData = new FormData(document.getElementById('registrationForm'));
    const data = {
        fullName: formData.get('full_name'),
        username: formData.get('username'),
        email: formData.get('email'),
        noTelp: formData.get('no_telp'),
        password: formData.get('password'),
        dateOfBirth: formData.get('date_of_birth'),
        gender: formData.get('gender'),
    };

    try {
        const response = await fetch('/api/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
                Accept: "application/json",
            },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            const errorData = await response.json();
            if (errorData.message === 'Username already exists') {
                usernameExistsError.style.display = 'block';
            }
            if (errorData.message === 'Email already exists') {
                document.getElementById('emailError').innerText = 'Akun sudah dibuat, silahkan ke halaman login';
                emailError.style.display = 'block';
            }
            throw new Error(errorData.message);
        }

        const responseData = await response.json();
        console.log('Success:', responseData);
        document.getElementById('successNotification').style.display = 'block';
        setTimeout(() => {
            window.location.href = "login.html";
        }, 5000);
    } catch (error) {
        console.log('Error:', error);
    }
});

