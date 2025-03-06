import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import axios from "axios";
import { EnvelopeFill, LightningFill, Clipboard } from "react-icons/bs";

const EmailAssistant3 = () => {
  const [emailContent, setEmailContent] = useState("");
  const [tone, setTone] = useState("Professional");
  const [customTone, setCustomTone] = useState("");
  const [aiResponse, setAiResponse] = useState("");
  const [loading, setLoading] = useState(false);
  const [showAlert, setShowAlert] = useState(false);

  const handleGenerateReply = async () => {
    setLoading(true);
    try {
      const response = await axios.post("http://localhost:8080/api/email/generate", {
        emailContent,
        tone: tone === "Others" ? customTone : tone,
      });
      setAiResponse(response.data);
    } catch (error) {
      console.error("Error calling backend:", error);
      setAiResponse("Failed to generate AI reply. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const handleClear = () => {
    setEmailContent("");
    setAiResponse("");
  };

  const handleCopy = () => {
    navigator.clipboard.writeText(aiResponse).then(() => {
      setShowAlert(true);
      setTimeout(() => setShowAlert(false), 2000);
    });
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100" style={{ background: "linear-gradient(to bottom, #e0efff, #ffffff)" }}>
      <div className="p-4 rounded-4 shadow-lg bg-white" style={{ maxWidth: "600px", width: "100%" }}>
        <h2 className="text-center fw-bold text-primary mb-3">
          <EnvelopeFill className="me-2" /> Email AI Assistant
        </h2>

        <div className="mb-3">
          <label className="form-label fw-semibold">
            <EnvelopeFill className="me-2" /> Email Content
          </label>
          <textarea
            className="form-control rounded-3"
            rows="5"
            value={emailContent}
            onChange={(e) => setEmailContent(e.target.value)}
          ></textarea>
        </div>

        <div className="mb-3">
          <label className="form-label fw-semibold">
            🎭 Select Tone
          </label>
          <select className="form-select rounded-3" value={tone} onChange={(e) => setTone(e.target.value)}>
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
              className="form-control rounded-3"
              value={customTone}
              onChange={(e) => setCustomTone(e.target.value)}
            />
          </div>
        )}

        <div className="d-flex justify-content-center gap-3">
          <button className="btn btn-primary rounded-pill px-4" onClick={handleGenerateReply} disabled={loading}>
            {loading ? "Generating..." : <><LightningFill className="me-2" /> Generate AI Reply</>}
          </button>
          <button className="btn btn-outline-danger rounded-pill px-4" onClick={handleClear}>Clear</button>
        </div>

        {showAlert && (
          <div className="alert alert-success mt-3" role="alert">
            Copied to clipboard! ✅
          </div>
        )}

        {aiResponse && (
          <div className="mt-4 p-3 border rounded bg-light position-relative">
            <h5>AI Generated Reply:</h5>
            <pre className="text-start" style={{ whiteSpace: "pre-wrap", wordWrap: "break-word" }}>{aiResponse}</pre>
            <button className="btn btn-outline-secondary btn-sm position-absolute top-0 end-0 m-2" onClick={handleCopy}>
              <Clipboard /> Copy
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default EmailAssistant3;
