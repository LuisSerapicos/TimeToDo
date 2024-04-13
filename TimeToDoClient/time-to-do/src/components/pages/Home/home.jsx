import { useContext, useEffect } from "react";
import { GlobalContext } from "../../context";
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Circles } from "react-loader-spinner";

export default function Home() {
    const { todoList, setTodoList, loading, setLoading } = useContext(GlobalContext)
    //const navigate = useNavigate()

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

    useEffect(() => {
        fetchTodoList()
    }, [])

    return (
        <div>
            <h2>Todo List</h2>
            {loading ? 
                <div className="min-h-screen w-full flex justify-center items-center">
                    <Circles height={"120"} width={"120"} color="rgb(127, 29, 29)" visible={true} />
                </div> : 
                (todoList && todoList.length > 0 ? 
                    <div className="grid sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                        {
                            todoList.map((todo, index) => {
                                return (
                                    <div key={index} className="flex w-[360px] justify-between items-center border border-gray-300 p-2 my-2 bg-blue-100 rounded-lg">
                                        <div>
                                            <p className="mb-5 text-left">Task: {todo.name}</p>
                                            <p className="text-left">Status: {todo.status}</p>
                                        </div>
                                        <div className="grid gap-2">
                                            <button className="bg-blue-500 text-white p-2 rounded" onClick={() => console.log('Edit')}>Edit</button>
                                            <button className="bg-red-500 text-white p-2 rounded" onClick={() => console.log('Delete')}>Delete</button>
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