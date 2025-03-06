console.log("Email Writer Extension - Content Script Loaded");

function createAIToneDropdown() {
    console.log("tone button created.")
    const dropdown = document.createElement("select");
    dropdown.className = "ai-tone-dropdown";
    dropdown.style.marginRight = "8px";

    const tones = ["Professional", "Polite", "Friendly", "Funny & Interacting", "Others"];
    tones.forEach(tone => {
        const option = document.createElement("option");
        option.value = tone.toLowerCase();
        option.textContent = tone;
        dropdown.appendChild(option);
    });

    return dropdown;
}
function createAIButton() {
   const button = document.createElement('div');
   button.className = 'T-I J-J5-Ji aoO v7 T-I-atl L3';
   button.style.marginRight = '8px';
   button.innerHTML = 'AI Reply';
   button.setAttribute('role','button');
   button.setAttribute('data-tooltip','Generate AI Reply');
   return button;
}

function getEmailContent() {  
    const selectors = [
        '.h7',
        '.a3s.aiL',
        '.gmail_quote',
        '[role="presentation"]'
    ];
    for (const selector of selectors) {
        const content = document.querySelector(selector);
        if (content) {
            return content.innerText.trim();
        }
        return '';
    }
}


function findComposeToolbar() {
    const selectors = [
        '.btC',
        '.aDh',
        '[role="toolbar"]',
        '.gU.Up'
    ];
    for (const selector of selectors) {
        const toolbar = document.querySelector(selector);
        if (toolbar) {
            return toolbar;
        }
        return null;
    }
}

function injectButton() {
    const existingButton = document.querySelector('.ai-reply-button');
    if (existingButton) existingButton.remove();

    const existingDropdown = document.querySelector(".ai-tone-dropdown");
    if (existingDropdown) existingDropdown.remove();

    const toolbar = findComposeToolbar();
    if (!toolbar) {
        console.log("Toolbar not found");
        return;
    }

    console.log("Toolbar found, creating AI button");
    
    const toneDropdown = createAIToneDropdown();

    
    const button = createAIButton();
    button.classList.add('ai-reply-button');

     // Listen for tone selection changes
     toneDropdown.addEventListener("change", async (event) => {
        if (event.target.value === "others") {
            const customTone = prompt("Enter your custom tone:");
            if (customTone) {
                // Add custom tone as the selected option
                event.target.options[event.target.selectedIndex].textContent = customTone;
                event.target.value = customTone;
            } else {
                event.target.value = "professional"; // Reset to default if no input
            }
        }
    });

    button.addEventListener('click', async () => {
        try {
            button.innerHTML = 'Generating...';
            button.disabled = true;

            const emailContent = getEmailContent();
            console.log("emailContent is received.");

            let selectedTone = toneDropdown.value;

            if (selectedTone === "others") {
                selectedTone = prompt("Enter your custom tone:");
                if (!selectedTone) {
                    alert("Tone cannot be empty!");
                    button.innerHTML = "AI Reply";
                    button.disabled = false;
                    return;
                }
            }

            console.log("Email Content received:", emailContent);
            console.log("Selected Tone:", selectedTone);


            const response = await fetch('http://localhost:8080/api/email/generate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    emailContent: emailContent,
                    // tone: "professional"
                    tone : selectedTone
                })
            });

            if (!response.ok) {
                throw new Error('API Request Failed');
            }

            const generatedReply = await response.text();
            const composeBox = document.querySelector('[role="textbox"][g_editable="true"]');

            if (composeBox) {
                composeBox.focus();
                document.execCommand('insertText', false, generatedReply);
            } else {
                console.error('Compose box was not found');
            }
        } catch (error) {
            console.error(error);
            alert('Failed to generate reply');
        } finally {
            button.innerHTML = 'AI Reply';
            button.disabled =  false;
        }
    });

    toolbar.insertBefore(button, toolbar.firstChild);
    toolbar.insertBefore(toneDropdown, button);
}

const observer = new MutationObserver((mutations) => {
    for(const mutation of mutations) {
        const addedNodes = Array.from(mutation.addedNodes);
        const hasComposeElements = addedNodes.some(node =>
            node.nodeType === Node.ELEMENT_NODE && 
            (node.matches('.aDh, .btC, [role="dialog"]') || node.querySelector('.aDh, .btC, [role="dialog"]'))
        );

        if (hasComposeElements) {
            console.log("Compose Window Detected");
            setTimeout(injectButton, 500);
        }
    }
});


observer.observe(document.body, {
    childList: true,
    subtree: true
});