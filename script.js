async function fetchData() {
    try {
        const response = await fetch("/api/events");

        if (!response.ok) {
            throw new Error("Could not fetch data");
        }

        const data = await response.json();
        console.log("Fetched data:", data);

        const events = data.map(event => ({
            eventName: event.eventName,
            event_img: event.eventImg,
            eventDesc: event.eventDescription,
        }));
        console.log("Mapped events:", events);

        const cardContainer = document.getElementById("card-container");
        cardContainer.innerHTML = "";

        if (events.length === 0) {
            cardContainer.innerHTML = "<p>No events found.</p>";
            return;
        }

        events.forEach(event => {
            addCard(event.eventName, event.event_img);
        });
    } catch (error) {
        console.log("Error fetching data:", error);
    }
}

function addCard(eventName, event_img, eventDesc) {
    const cardContainer = document.getElementById("card-container");

    const card = document.createElement("div");
    card.className = "col-md-4"; // Changed from col-sm-4 to col-md-4 to ensure 3 cards per row on medium devices and up

    card.innerHTML = `
        <div class="custom-card">
            <div class="img-box">
            <a href="detailEvent.html?name=${encodeURIComponent(eventName)}">
                <img src="data:image/jpeg;base64,${event_img}" class="card-img-top" alt="${eventName}">
            </a>
            </div>
            <div class="custom-content">
                <h2>${eventName}</h2>
                <p>${eventDesc}</p>
                <a href="detailEvent.html?name=${encodeURIComponent(eventName)}">Read More</a>
            </div>    
    `;

    cardContainer.appendChild(card);
}

async function login(event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/api/users/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            throw new Error("Login failed");
        }

        const data = await response.json();

        if (data.success) {
            console.log("Successful login");
            window.location.href = "#";
        } else {
            document.getElementById("login-message").innerText = "Invalid email or password";
        }
    } catch (error) {
        console.log("Error during login:", error);
    }
}

document.addEventListener("DOMContentLoaded", fetchData);
document.getElementById("loginForm").addEventListener("submit", login);
