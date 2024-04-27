import { useContext, useEffect } from "react"
import { GlobalContext } from "../../context"
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';


export default function AddTask() {
    const { formData, setFormData, setLoading, isEdit, setIsEdit } = useContext(GlobalContext)
    const navigate = useNavigate()
    const location = useLocation()

    async function handleSaveTask() {
        setLoading(true);

        try {
            const response = !isEdit ? await axios.post('http://localhost:8080/api/v1/tasks', {
                name: formData.name,
                status: formData.status,
                description: formData.description
            }) : 
            await axios.put(`http://localhost:8080/api/v1/tasks/${location.state.currentTodo.id}`, {
                name: formData.name,
                status: formData.status,
                description: formData.description
            })

            if(response?.status === 200 || response?.status === 201) {
                setFormData({ name: "", status: "", description: "" })
                setLoading(false)
                setIsEdit(false)
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
        setIsEdit(false)
        navigate('/')
    }

    useEffect(() => {
        if (location.state) {
            const { currentTodo } = location.state

            setIsEdit(true)
            setFormData({
                name: currentTodo.name,
                status: currentTodo.status,
                description: currentTodo.description
            })
        }
    }, [location])

    
    return (
        <div className="w-[360px] mx-auto">
            <h2>
                {
                    isEdit ? "Edit Task" : "Add New Task"
                }
            </h2>
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
                    <button className="w-full mr-1 bg-blue-500 text-white p-2 rounded-lg" onClick={handleSaveTask}>
                        {
                            isEdit ? "Edit Task" : "Add Task"
                        }
                    </button>
                    <button className="w-full bg-red-500 text-white p-2 rounded-lg" onClick={handleCancel}>Cancel</button>
                </div>
            </div>
        </div>
    )
}