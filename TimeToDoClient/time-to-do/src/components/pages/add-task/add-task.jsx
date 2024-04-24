import { useContext, useState } from "react"
import { GlobalContext } from "../../context"
import { useNavigate } from 'react-router-dom';
import axios from 'axios';


export default function AddTask() {
    const { formData, setFormData, loading, setLoading } = useContext(GlobalContext)
    const navigate = useNavigate()

    async function handleSaveTask() {
        setLoading(true);

        try {
            const response = await axios.post('http://localhost:8080/api/v1/tasks', {
                name: formData.name,
                status: formData.status,
                description: formData.description
            })
            const result = await response.data
            console.log(result)

            if(result) {
                setFormData({ name: "", status: "", description: "" })
                setLoading(false)
                navigate('/')
            }
            else {
                setLoading(false)
            }
        } catch(err) {
            console.log(err)
            setLoading(false)
        }
    }

    function handleCancel() {
        setFormData({ name: "", status: "", description: "" })
        navigate('/')
    }

    
    return (
        <div className="w-[360px] mx-auto">
            <h2>Add New Task</h2>
            <div className="flex flex-col m-4">
                <input type="text" placeholder="Task Name" className="bg-blue-100 border border-gray-300 p-2 my-2 rounded-lg" value={formData.name} onChange={(e) => setFormData({...formData, name: e.target.value})} />
                <select className="bg-blue-100 border border-gray-300 p-2 my-2 rounded-lg" value={formData.status} onChange={(e) => setFormData({...formData, status: e.target.value})}>
                    <option value="">Select the status</option>
                    <option value="TODO">To Do</option>
                    <option value="DOING">Doing</option>
                    <option value="DONE">Done</option>
                </select>
                <input type="text" placeholder="Task Description" className="bg-blue-100 border border-gray-300 p-2 my-2 rounded-lg" value={formData.description} onChange={(e) => setFormData({...formData, description: e.target.value})} />
                <div className="flex w-full justify-between">
                    <button className="w-full mr-1 bg-blue-500 text-white p-2 rounded-lg" onClick={handleSaveTask}>Add Task</button>
                    <button className="w-full bg-red-500 text-white p-2 rounded-lg" onClick={handleCancel}>Cancel</button>
                </div>
            </div>
        </div>
    )
}