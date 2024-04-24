import { useState } from "react";
import { createContext } from "react";


export const GlobalContext = createContext();

export default function GlobalState({ children }) {
    const [formData, setFormData] = useState({ name: "", status: "", description: "" })
    const [todoList, setTodoList] = useState([])
    const [loading, setLoading] = useState(false)
    const [isEdit, setIsEdit] = useState(false)

    return (
        <GlobalContext.Provider value={ {formData, setFormData, todoList, setTodoList, loading, setLoading, isEdit, setIsEdit} }>
        {children}
        </GlobalContext.Provider>
    );
}