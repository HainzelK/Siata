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
          event_img: event.eventImg, // Ensure this matches the actual property name for the image
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
      console.error("Error fetching data:", error);
  }
}

function addCard(eventName, event_img) {
  const cardContainer = document.getElementById("card-container");

  const card = document.createElement("div");
  card.className = "col-sm-4 mb-3";

  card.innerHTML = `
      <div class="card">
          <a href="detailEvent.html?name=${encodeURIComponent(eventName)}">
              <img src="data:image/jpeg;base64,${event_img}" class="card-img-top" alt="${eventName}">
          </a>
          <div class="card-body">
              <h5 class="card-title text-center">${eventName}</h5>
          </div>
      </div>
  `;

  cardContainer.appendChild(card);
}

document.addEventListener("DOMContentLoaded", fetchData);
