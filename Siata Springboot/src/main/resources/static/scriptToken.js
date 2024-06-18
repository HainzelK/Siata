function getCookie(name) {
    const cookieArr = document.cookie.split(";");
    for (let i = 0; i < cookieArr.length; i++) {
        const cookiePair = cookieArr[i].split("=");
        if (name === cookiePair[0].trim()) {
            return decodeURIComponent(cookiePair[1]);
        }
    }
    return null;
}

function deleteCookie(name) {
    document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/`;
}

async function searchUserIdByEmail(email) {
    try {
        const response = await fetch(`/api/user/search?email=${email}`);
        if (!response.ok) {
            throw new Error('Failed to search user ID by email');
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error in searchUserIdByEmail:', error);
        throw error;
    }
}

function extractEmailFromToken(token) {
    try {
        const tokenPayload = token.split('.')[1];
        const decodedPayload = atob(tokenPayload);
        const payloadObject = JSON.parse(decodedPayload);
        return payloadObject.sub;
    } catch (error) {
        console.error('Error extracting email from JWT token:', error);
        return null;
    }
}

async function getUserInfo() {
    const token = getCookie('accessToken');
    const userInfoDiv = document.getElementById('user-info');

    if (!token) {
        userInfoDiv.innerHTML = '<p style="color: red;">Token is missing from cookies</p>';
        return;
    }

    try {
        const email = extractEmailFromToken(token);
        if (!email) {
            throw new Error('Email not found in token');
        }

        const userId = await searchUserIdByEmail(email);
        // console.log('User ID:', userId); // Log the user ID to the console

        const userDataResponse = await fetch(`/api/user/id/${userId}`);
        if (!userDataResponse.ok) {
            throw new Error('Failed to fetch user data');
        }

        const userData = await userDataResponse.json();
        userInfoDiv.innerHTML = `
            <p><strong>Name:</strong> ${userData.username}</p>
            <p><strong>Email:</strong> ${userData.email}</p>
            <p><strong>Phone:</strong> ${userData.noTelp}</p>

        `;
    } catch (error) {
        userInfoDiv.innerHTML = '<p style="color: red;">' + error.message + '</p>';
        console.error('Error in getUserInfo:', error);
    }
}

async function logout() {
    try {
        const response = await fetch('/auth/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + getCookie('accessToken')
            }
        });
        if (!response.ok) {
            throw new Error('Logout failed');
        }

        // Delete the access token cookie
        deleteCookie('accessToken');
        deleteCookie('refreshToken');

        // Clear user interface
        

        // Optionally redirect or reload
        window.location.href = '/login.html';
    } catch (error) {
        console.error('Error during logout:', error);
    }
}

async function checkLoginStatus() {
    const token = getCookie('accessToken');
    const loginSignupButton = document.getElementById('login-signup-btn');
    const profileLink = document.getElementById('profile-link');
    const navContainer = document.querySelector('.nav');

    if (token) {
        try {
            const email = extractEmailFromToken(token);
            // console.log(`Logged in as ${email}`); // Display the email from token
            const userId = await searchUserIdByEmail(email);
            loginSignupButton.style.display = 'none';
            profileLink.style.display = 'inline';
            navContainer.appendChild(profileLink); // Move profile to the end
            // console.log(`User id : ${userId}`)
        } catch (error) {
            console.error('Error checking login status:', error);
            loginSignupButton.style.display = 'inline';
            profileLink.style.display = 'none';
            navContainer.appendChild(loginSignupButton); // Move login/signup to the end
        }
    } else {
        // No token found, user is not logged in
        loginSignupButton.style.display = 'inline';
        profileLink.style.display = 'none';
        navContainer.appendChild(loginSignupButton); // Move login/signup to the end
    }
}

// Call checkLoginStatus on page load
document.addEventListener('DOMContentLoaded', checkLoginStatus);

// Add event listener to logout button
document.getElementById('logout-button').addEventListener('click', logout);
