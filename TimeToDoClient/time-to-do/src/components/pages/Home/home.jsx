import { useContext, useEffect } from "react";
import { GlobalContext } from "../../context";
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

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
            Home
        </div>
    );
}