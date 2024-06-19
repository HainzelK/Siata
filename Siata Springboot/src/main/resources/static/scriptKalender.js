document.addEventListener("DOMContentLoaded", () => {
  const calendar = document.getElementById("calendar-table");
  const gridTable = document.getElementById("table-body");
  const currentDate = new Date();
  let selectedDate = currentDate;
  let selectedDayBlock = null;
  const globalEventObj = {};
  const sidebar = document.getElementById("sidebar");

  function createCalendar(date, side) {
     const startDate = new Date(date.getFullYear(), date.getMonth(), 1);
     const monthTitle = document.getElementById("month-name");
     const monthName = date.toLocaleString("en-US", { month: "long" });
     const yearNum = date.toLocaleString("en-US", { year: "numeric" });
     monthTitle.innerHTML = `${monthName} ${yearNum}`;
 
     if (side == "left") {
         gridTable.className = "animated fadeOutRight";
     } else {
         gridTable.className = "animated fadeOutLeft";
     }
 
     setTimeout(() => {
         gridTable.innerHTML = "";
         let newTr = document.createElement("div");
         newTr.className = "row";
         let currentTr = gridTable.appendChild(newTr);
 
         for (let i = 1; i < (startDate.getDay() || 7); i++) {
             let emptyDivCol = document.createElement("div");
             emptyDivCol.className = "col empty-day";
             currentTr.appendChild(emptyDivCol);
         }
 
         const lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
 
         for (let i = 1; i <= lastDay; i++) {
             if (currentTr.children.length >= 7) {
                 currentTr = gridTable.appendChild(addNewRow());
             }
 
             let currentDay = document.createElement("div");
             currentDay.className = "col";
             if (new Date().toDateString() === new Date(date.getFullYear(), date.getMonth(), i).toDateString()) {
              currentDay.style.backgroundColor = "#395886"; //warna di kalender utk hari ini
              currentDay.style.color = "white"; // Optional: Change text color to white for better readability
          }
             if (selectedDayBlock == null && i == date.getDate() || selectedDate.toDateString() == new Date(date.getFullYear(), date.getMonth(), i).toDateString()) {
                 selectedDate = new Date(date.getFullYear(), date.getMonth(), i);
                 document.getElementById("eventDayName").innerHTML = selectedDate.toLocaleString("en-US", { month: "long", day: "numeric", year: "numeric" });
                 selectedDayBlock = currentDay;
                 setTimeout(() => {
                     currentDay.classList.add("blue", "lighten-3");
                 }, 900);
             }
             currentDay.innerHTML = i;
 
             if (globalEventObj[new Date(date.getFullYear(), date.getMonth(), i).toDateString()]) {
                 let eventMark = document.createElement("div");
                 eventMark.className = "day-mark";
                 currentDay.appendChild(eventMark);
             }
 
             // Grey out past dates
             let currentDate = new Date();
             let cellDate = new Date(date.getFullYear(), date.getMonth(), i);
             if (cellDate < currentDate.setHours(0,0,0,0)) {
                 currentDay.classList.add("greyed-out");
             }
 
             currentTr.appendChild(currentDay);
         }
 
         for (let i = currentTr.getElementsByTagName("div").length; i < 7; i++) {
             let emptyDivCol = document.createElement("div");
             emptyDivCol.className = "col empty-day";
             currentTr.appendChild(emptyDivCol);
         }
 
         if (side == "left") {
             gridTable.className = "animated fadeInLeft";
         } else {
             gridTable.className = "animated fadeInRight";
         }
     }, !side ? 0 : 270);
 
     fetchData();
 }
 

  createCalendar(currentDate);

  document.getElementById("todayDayName").innerHTML = "Today is " + currentDate.toLocaleString("en-US", { weekday: "long", day: "numeric", month: "short" });

  document.getElementById("prev").onclick = () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    createCalendar(currentDate, "left");
  };

  document.getElementById("next").onclick = () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    createCalendar(currentDate, "right");
  };

  function addEvent(title, desc) {
    if (!globalEventObj[selectedDate.toDateString()]) {
      globalEventObj[selectedDate.toDateString()] = {};
    }
    globalEventObj[selectedDate.toDateString()][title] = desc;
  }

  function showEvents() {
     const sidebarEvents = document.getElementById("sidebarEvents");
     const objWithDate = globalEventObj[selectedDate.toDateString()];
     sidebarEvents.innerHTML = "";
 
     if (objWithDate) {
         let eventsCount = 0;
         for (let key in objWithDate) {
             let eventContainer = document.createElement("div");
             eventContainer.className = "eventCard";
             eventContainer.addEventListener("click", () => {
                 window.location.href = `detailEvent.html?name=${encodeURIComponent(key)}`;
             });
 
             let eventHeader = document.createElement("div");
             eventHeader.className = "eventCard-header";
             let eventDescription = document.createElement("div");
             // eventDescription.className = "eventCard-description";
 
             eventHeader.textContent = key;
             eventDescription.textContent = objWithDate[key];
             eventContainer.append(eventHeader);
 
             let markWrapper = document.createElement("div");
             markWrapper.className = "eventCard-mark-wrapper";
             let mark = document.createElement("div");
             mark.classList = "eventCard-mark";
             markWrapper.appendChild(mark);
             eventContainer.appendChild(markWrapper);
 
             // Grey out past events
             let currentDate = new Date();
             if (selectedDate < currentDate.setHours(0,0,0,0)) {
                 eventContainer.classList.add("greyed-out");
                 mark.classList.add("greyed-out");
             }
 
             sidebarEvents.appendChild(eventContainer);
             eventsCount++;
         }
         document.getElementById("emptyFormTitle").innerHTML = `There are ${eventsCount} events today`;
     } else {
         let emptyMessage = document.createElement("div");
         emptyMessage.className = "empty-message";
         emptyMessage.innerHTML = "No events on the selected date.";
         sidebarEvents.appendChild(emptyMessage);
         document.getElementById("emptyFormTitle").innerHTML = "No events today.";
     }
 }
  
  gridTable.addEventListener("click", (e) => {
    if (!e.target.classList.contains("col") || e.target.classList.contains("empty-day")) {
      return;
    }

    if (selectedDayBlock) {
      selectedDayBlock.classList.remove("blue", "lighten-3");
    }

    selectedDayBlock = e.target;
    selectedDayBlock.classList.add("blue", "lighten-3");
    selectedDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), parseInt(e.target.innerHTML));
    document.getElementById("eventDayName").innerHTML = selectedDate.toLocaleString("en-US", { month: "long", day: "numeric", year: "numeric" });
    showEvents();
  });

  document.getElementById("changeFormButton").onclick = () => {
    document.getElementById("addForm").style.top = 0;
  };

  document.getElementById("cancelAdd").onclick = () => {
    const addForm = document.getElementById("addForm");
    addForm.style.top = "100%";
    addForm.querySelectorAll("input").forEach(input => input.value = "");
    addForm.querySelectorAll("label").forEach(label => label.className = "");
  };

  document.getElementById("addEventButton").onclick = () => {
    const title = document.getElementById("eventTitleInput").value.trim();
    const desc = document.getElementById("eventDescInput").value.trim();

    if (!title || !desc) {
      document.getElementById("eventTitleInput").value = "";
      document.getElementById("eventDescInput").value = "";
      document.getElementById("addForm").querySelectorAll("label").forEach(label => label.className = "");
      return;
    }

    addEvent(title, desc);
    showEvents();

    if (!selectedDayBlock.querySelector(".day-mark")) {
      selectedDayBlock.appendChild(document.createElement("div")).className = "day-mark";
    }

    document.getElementById("addForm").querySelectorAll("input").forEach(input => input.value = "");
    document.getElementById("addForm").querySelectorAll("label").forEach(label => label.className = "");
  };

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
      const calendarEvents = data.map(event => ({
        
        event_id: event.eventId,
        eventName: event.eventName,
        start_time: event.eventDate,
        summary: event.eventDescription
      }))

      calendarEvents.forEach(event => {
        const eventDate = new Date(event.start_time);
        const dateString = eventDate.toDateString();

        if (!globalEventObj[dateString]) {
          globalEventObj[dateString] = {};
        }
        globalEventObj[dateString][event.eventName] = event.eventName;
      });

      calendarEvents.forEach(event => {
        const eventDate = new Date(event.start_time);
        if (eventDate.getMonth() === currentDate.getMonth() && eventDate.getFullYear() === currentDate.getFullYear()) {
          const dateElement = Array.from(gridTable.querySelectorAll('.col')).find(el => el.innerText == eventDate.getDate() && !el.classList.contains("empty-day"));
          if (dateElement && !dateElement.querySelector('.day-mark')) {
            dateElement.appendChild(document.createElement("div")).className = "day-mark";
          }
        }
      });
    } catch (error) {
      console.error(error);
    }
  }
});

function addNewRow() {
  let node = document.createElement("div");
  node.className = "row";
  return node;
}
