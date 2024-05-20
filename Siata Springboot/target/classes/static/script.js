document.addEventListener("DOMContentLoaded", function () {
  const eventForm = document.getElementById("eventForm");
  const eventList = document.getElementById("eventList");

  function loadEvents() {
    fetch("/api/events")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((events) => {
        eventList.innerHTML = "";
        events.forEach((event) => {
          const li = document.createElement("li");
          li.textContent = `${event.eventName} - ${event.eventDescription} - ${event.eventDate} - ${event.eventTime} - ${event.location}`;
          eventList.appendChild(li);
        });
      })
      .catch((error) => {
        console.error("There has been a problem with your fetch operation:", error);
      });
  }

  eventForm.addEventListener("submit", function (e) {
    e.preventDefault();
    const formData = new FormData(eventForm);
    const event = {
      eventName: formData.get("eventName"),
      eventDescription: formData.get("eventDescription"),
      eventDate: formData.get("eventDate"),
      eventTime: formData.get("eventTime"),
      location: formData.get("location"),
    };

    fetch("/api/events", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(event),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        console.log(data.message); // Log the success message
        eventForm.reset();
        loadEvents();
      })
      .catch((error) => {
        console.error("There has been a problem with your fetch operation:", error);
      });
  });
  loadEvents();
});
