document.addEventListener("DOMContentLoaded", function () {
  const eventForm = document.getElementById("eventForm");
  const eventList = document.getElementById("eventList");
  const imgInput = document.getElementById("imgInput");

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
          const img = document.createElement("img");
          img.src = `data:image/jpeg;base64,${event.eventImg}`; // Set image source to the Base64 string with proper prefix for display
          img.alt = "Event Image";
          img.style.maxWidth = "200px"; // Set a max width for the image for better visibility
          img.style.margin = "10px"; // Add margin for spacing
          li.textContent = `${event.eventName} - ${event.eventDescription} - ${event.eventDate} - ${event.eventTime} - ${event.location}`;
          li.appendChild(img); // Append the image to the list item
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
    const file = imgInput.files[0];

    // Validate file size
    if (file.size > 5000000) {
      // File size limit is 5MB
      alert("Image size is too large. Please upload an image smaller than 5MB.");
      return;
    }

    const reader = new FileReader();

    reader.readAsDataURL(file);
    reader.onload = function () {
      formData.set("eventImg", reader.result); // Save image as a Base64 string

      const jsonData = {};
      formData.forEach((value, key) => {
        jsonData[key] = value;
      });

      // Ensure the image is encoded in Base64 before sending
      if (reader.result && reader.result.startsWith("data:image")) {
        fetch("/api/events", {
          method: "POST",
          body: JSON.stringify(jsonData),
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        })
          .then((response) => {
            if (!response.ok) {
              return response.json().then((err) => {
                throw new Error(err.message);
              });
            }
            return response.json();
          })
          .then((data) => {
            console.log(data.message);
            eventForm.reset();
            loadEvents();
          })
          .catch((error) => {
            console.error("There has been a problem with your fetch operation:", error);
          });
      } else {
        console.error("Failed to load image as a Base64 string.");
      }
    };
  });

  loadEvents();
});