import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import axios from 'axios'
import React,{useState, useEffect,useCallback} from 'react'
import {useDropzone} from 'react-dropzone'

//Function must start with an uppercase
const UserProfiles = () =>{
  //use []brackets instead of {}curly braces
  const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles=()=>{

    axios.get("http://localhost:8080/api/v1/user-profile").then(res =>{
      console.log(res);
      const data = res.data;
      setUserProfiles(data)
    });

  }

  useEffect(()=>{
    fetchUserProfiles();
  }, []);

  return userProfiles.map((userProfile,index) => {
    {/* The map will grab the UserProfile class and index is the
      number for each UserProfile Object*/}
    return (
      <div key={index}>{/*for each index create this div */}
          {userProfile.userProfileId ? (
              <img
              src={`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileId}/image/download`}/>)
              :null}

        {/*todo: profile image*/}
        <br/>
        <br/>
        <h1>{userProfile.username}</h1>{/*grabs the username
        from the UserProfile class*/}
        <p>{userProfile.userProfileId}</p>{/*grabs the Id
        from the UserProfile class*/}
        <DropZone {...userProfile}/>
        {/*DropZone is will pass the grab the UserProfile
        userProfileId=userProfile.userProfileId*/}
        <br/>
      </div>
    );

  });



};

function DropZone({userProfileId}){
  const onDrop = useCallback(acceptedFiles =>{
    //something with files
    const file=acceptedFiles[0];
    console.log(file);

    const formData =new FormData();
    formData.append("file",file);
    axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
      formData,
      {
        headers:{
          "Content-Type": "multipart/form-data"
        }
      }
    ).then(() => {
      console.log("file uploaded successfully")
    }).catch(err=>{
      console.log(err);
    })

  }, [])

  const{getRootProps, getInputProps, isDragActive}= useDropzone({ onDrop })

  return(
    <div{...getRootProps()}>
      {/*<input {...getRootProps()}/>*/}
      {isDragActive ? (
        <p>Drop the image here</p>
      ) : (
        <p>Drag and drop profile image, or click select profile image</p>
        )}

    </div>
  )

  
}

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className='App'>
      {/* <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
        and learn how to build your own Vite + React app.
      </p> */}
      <UserProfiles />
    </div>
  )
}

export default App
