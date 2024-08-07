import { useContext, useEffect } from "react";
import { GlobalContext } from "../../context";
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Circles } from "react-loader-spinner";
import Details from "../details/details";
import Modal from "react-modal"

export default function Home() {
    const { todoList, setTodoList, loading, setLoading, modalIsOpen, setModalIsOpen, setCurrentTodo } = useContext(GlobalContext)
    const navigate = useNavigate()
    

    async function fetchTodoList() {
        setLoading(true);

        try {
            const response = await axios.get('http://localhost:8080/api/v1/tasks/all')
            const result = await response.data
            console.log(result)

            if(result && result.length > 0) {
                setTodoList(result)
                setLoading(false)
            }
            else {
                setLoading(false)
                setTodoList([])
            }
        } catch(err) {
            console.log(err)
            setLoading(false)
            setTodoList([])
        }
    }

    function handleDetails(currentTodo) {
        console.log(currentTodo.description)
        setCurrentTodo(currentTodo);
        setModalIsOpen(true);
    }

    function handleEdit(currentTodo) {
        navigate("/add-task", { state: { currentTodo } })
    }

    async function handleDelete(id) {
        try {
            const response = await axios.delete(`http://localhost:8080/api/v1/tasks/${id}`)

            if(response?.status === 200) {
                fetchTodoList()
            }
            else {
                setLoading(false)
                console.log("Delete operation failed with status code:", response.status);
            }
        } catch(err) {
            console.log(err)
            setLoading(false)
        }
    }

    useEffect(() => {
        Modal.setAppElement("#root")    // This is required for accessibility(prevent screen readers from reading the content behind the modal)
        fetchTodoList()
    }, [])

    return (
        <div>
            {
                modalIsOpen ? <Details /> : null
            }
            <div className="grid items-center sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-2 gap-2 w-[400px] sm:w-[400px] md:w-3/4 lg:w-full">
                <h2>Your Tasks</h2>
                <Link to={"/add-task"}>
                    <button className="bg-blue-500 text-white p-2 rounded-lg">Add Task</button>
                </Link>
            </div>
            {loading ? 
                <div className="min-h-screen w-full flex justify-center items-center">
                    <Circles height={"120"} width={"120"} color="#5484d1" visible={true} />
                </div> : 
                (todoList && todoList.length > 0 ? 
                    <div className="grid sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4 m-4">
                        {
                            todoList.map((todo, index) => {
                                return (
                                    <div onClick={() => handleDetails(todo)} key={index} className="flex w-[315px] justify-between items-center border border-gray-300 p-2 my-2 bg-blue-500 rounded-lg">
                                        <div>
                                            <p className="mb-5 text-left text-white">{todo.name}</p>
                                            <p className="text-left text-white">{todo.status}</p>
                                        </div>
                                        <div className="grid gap-2">
                                            <button className="bg-yellow-500 text-white p-2 rounded" onClick={(e) => {e.stopPropagation(); handleEdit(todo);}}>Edit</button>
                                            <button className="bg-red-500 text-white p-2 rounded" onClick={(e) => {e.stopPropagation(); handleDelete(todo.id);}}>Delete</button>
                                        </div>
                                    </div>
                                )
                            })
                        }
                    </div> : 
                    <div>
                        <h3>No tasks yet!</h3>
                    </div>)
            }
        </div>
    );
}