import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import axios from "axios";

// import { Clipboard } from "react-bootstrap-icons"; // Import Bootstrap icon

const EmailAssistant = () => {
  const [emailContent, setEmailContent] = useState("");
  const [tone, setTone] = useState("Professional");
  const [customTone, setCustomTone] = useState("");
  const [aiResponse, setAiResponse] = useState("");
  const [loading, setLoading] = useState(false);
//   const [copied, setCopied] = useState(false); // State for copy feedback
  const [showAlert, setShowAlert] = useState(false); // Alert state

  const handleGenerateReply = async () => {
    if (!emailContent.trim()) {
      setAiResponse("⚠️ Please enter email content before generating a reply.");
      return;
    }
    setLoading(true);
    try {
      const response = await axios.post("http://localhost:8080/api/email/generate", {
        emailContent,
        tone: tone === "Others" ? customTone : tone,
      });

      setAiResponse(response.data); // Set AI-generated response
      console.log("AI Response:", response.data);
    } catch (error) {
      console.error("Error calling backend:", error);
      setAiResponse("Failed to generate AI reply. Please try again.");
    } finally {
      setLoading(false);
    }
  };
  const handleClear = () => {
    setAiResponse("");
  }

  // Function to copy AI response
  const handleCopy = () => {
    navigator.clipboard.writeText(aiResponse)
    .then(()=>{
        setShowAlert(true);//show alert
        setTimeout(() => setShowAlert(false), 2000);
    })
    .catch((err)=>console.error("❌ Failed to copy text:",err));
  };

  return (
    // <div className="container mt-4">
    <div className="container-fluid p-3">
      <div className="border rounded p-4 shadow bg-white" style={{ boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)", border: "1px solid #ddd" }}>

      <h1>Email AI Assistant</h1>
      
      <div className="mb-3">
        <label className="form-label">Email Content</label>
        <textarea
          className="form-control"
          rows="5"
          value={emailContent}
          onChange={(e) => setEmailContent(e.target.value)}
        ></textarea>
      </div>

      <div className="mb-3">
        <label className="form-label">Select Tone</label>
        <select
          className="form-select"
          value={tone}
          onChange={(e) => setTone(e.target.value)}
        >
          <option>Professional</option>
          <option>Friendly</option>
          <option>Casual</option>
          <option>Polite</option>
          <option>Others</option>
        </select>
      </div>

      {tone === "Others" && (
        <div className="mb-3">
          <label className="form-label">Custom Tone</label>
          <input
            type="text"
            className="form-control"
            value={customTone}
            onChange={(e) => setCustomTone(e.target.value)}
          />
        </div>
      )}

      <button className="btn btn-primary me-2" onClick={handleGenerateReply} disabled={loading}>
        {loading ? "Generating..." : "Generate AI Reply"}
      </button>
      {aiResponse && (
        <button className="btn btn-danger" onClick={handleClear} disabled={loading}>
          Clear
        </button>
      )}
      <br></br>
      {/* Success Alert */}
      {showAlert && (
        <div className="alert alert-success alert-dismissible fade show mt-3" role="alert">
            Copied to clipboard! ✅
        </div>
      )}

      {aiResponse && (
        <div className="mt-4 p-3 border rounded bg-light position-relative">
          <h5>AI Generated Reply:</h5>
          <pre className="text-start" style={{ whiteSpace: "pre-wrap", wordWrap: "break-word" }}>
            {aiResponse}
          </pre>

          {/* Copy Button */}
          <button
            className="btn btn-outline-secondary btn-sm position-absolute top-0 end-0 m-2"
            onClick={handleCopy}
            title="Copy to clipboard"
          >
            {/* <Clipboard />  */}
            copy
          </button>
        </div>
      )}
    </div>
    </div>
  );
};

export default EmailAssistant;
