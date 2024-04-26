import logo from "./images/timetodo-high-resolution-logo-white-transparent.png";
import './App.css';
import { Routes, Route } from 'react-router-dom';
import Home from './components/pages/Home/home';
import AddTask from './components/pages/add-task/add-task';
import Header from './components/header';

function App() {
  return (
    <div className="App">
      <Header img={logo} />
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/add-task' element={<AddTask />} />
      </Routes>
    </div>
  );
}

export default App;
