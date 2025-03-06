import { useState } from 'react'
import './App.css'
import { Box, Container, TextField, Typography } from '@mui/material';

function App() {
  const [emailContent, setEmailContent] = useState('');
  const [tone,setTone] = useState('');
  const [generatedReply,setGeneratedReply] = useState('');
  const [loading,setLoading] = useState(false);
  const [error,setError] = useState('');

  return (
    // <Container maxWidth="md" sx={{py:4}}>
    <Container 
      maxWidth={false} 
      disableGutters 
      sx={{
        height: '100vh',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'flex-start',
        alignItems: 'flex-start',
        padding: 3, // Adjust as needed
      }}
    >
      <Typography variant ='h3' component ='h1' gutterBottom>
        SRS Email Assistant
      </Typography>
      <Box sx = {{mx:3}}>
        <TextField 
          fullWidth
          multiline
          rows={6}
          variant ='outlined'
          label="Original Email Content"
          value={emailContent || ''}
          onChange={(e)=> setEmailContent(e.target.value)}
          sx = {{mb:2}}
        />
      </Box>
    </Container>
  )
}

export default App
