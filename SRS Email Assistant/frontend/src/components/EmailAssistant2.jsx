import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import axios from "axios";

const EmailAssistant2 = () => {
  const [emailContent, setEmailContent] = useState("");
  const [tone, setTone] = useState("Professional");
  const [customTone, setCustomTone] = useState("");
  const [aiResponse, setAiResponse] = useState("");
  const [loading, setLoading] = useState(false);
  const [showAlert, setShowAlert] = useState(false);
  const [copied, setCopied] = useState(false);

  const handleGenerateReply = async () => {
    if (!emailContent.trim()) {
      setAiResponse("⚠️ Please enter email content before generating a reply.");
      return;
    }

    setLoading(true);
    setAiResponse("");

    try {
      const response = await axios.post("http://localhost:8080/api/email/generate", {
        emailContent,
        tone: tone === "Others" ? customTone : tone,
      });

      setAiResponse(response.data);
    } catch (error) {
      console.error("Error calling backend:", error);
      setAiResponse("❌ Failed to generate AI reply. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const handleCopy = () => {
    navigator.clipboard.writeText(aiResponse)
      .then(() => {
        setCopied(true);
        setShowAlert(true);
        setTimeout(() => {
          setShowAlert(false);
          setCopied(false);
        }, 2000);
      })
      .catch((err) => console.error("Failed to copy text:", err));
  };

  const handleClear = () => {
    setAiResponse("");
    setCopied(false);
  };

  return (
    <div className="min-vh-100 d-flex justify-content-center align-items-center" 
         style={{ background: "linear-gradient(to right, #e3f2fd, #bbdefb)" }}>
      <div className="card shadow-lg p-4" style={{ width: "50rem", borderRadius: "15px" }}>
        <h2 className="text-center text-primary mb-4">📩 Email AI Assistant</h2>

        <div className="mb-3">
          <label className="form-label fw-bold">✉️ Email Content</label>
          <textarea
            className="form-control border-2"
            rows="5"
            value={emailContent}
            onChange={(e) => setEmailContent(e.target.value)}
          ></textarea>
        </div>

        <div className="mb-3">
          <label className="form-label fw-bold">🎭 Select Tone</label>
          <select className="form-select border-2" value={tone} onChange={(e) => setTone(e.target.value)}>
            <option>Professional</option>
            <option>Friendly</option>
            <option>Casual</option>
            <option>Polite</option>
            <option>Others</option>
          </select>
        </div>

        {tone === "Others" && (
          <div className="mb-3">
            <label className="form-label fw-bold">🎨 Custom Tone</label>
            <input
              type="text"
              className="form-control border-2"
              value={customTone}
              onChange={(e) => setCustomTone(e.target.value)}
            />
          </div>
        )}

        <div className="d-flex justify-content-center mt-3">
          <button className="btn btn-primary me-3 px-4" onClick={handleGenerateReply} disabled={loading}>
            {loading ? "Generating..." : "⚡ Generate AI Reply"}
          </button>

          {aiResponse && (
            <button className="btn btn-secondary px-4" onClick={handleClear} disabled={loading}>
              ❌ Clear
            </button>
          )}
        </div>

        {showAlert && (
          <div className="alert alert-success fade show mt-3 text-center" role="alert">
            ✅ Copied to clipboard!
          </div>
        )}

        {aiResponse && (
          <div className="mt-4 p-3 border rounded bg-light position-relative">
            <h5 className="fw-bold text-success">💡 AI Generated Reply:</h5>
            <pre className="text-start" style={{ whiteSpace: "pre-wrap", wordWrap: "break-word" }}>
              {aiResponse}
            </pre>

            <button
              className="btn btn-outline-secondary btn-sm position-absolute top-0 end-0 m-2"
              onClick={handleCopy}
              title="Copy to clipboard"
            >
              {copied ? "✅ Copied!" : "📋 Copy"}
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default EmailAssistant2;
