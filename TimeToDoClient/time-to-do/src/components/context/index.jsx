import { useState } from "react";
import { createContext } from "react";


export const GlobalContext = createContext();

export default function GlobalState({ children }) {
    const [formData, setFormData] = useState({ name: "", status: "", description: "" })
    const [todoList, setTodoList] = useState([])
    const [loading, setLoading] = useState(false)
    const [isEdit, setIsEdit] = useState(false)
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [currentTodo, setCurrentTodo] = useState(null);
    const [formErrors, setFormErrors] = useState({})

    return (
        <GlobalContext.Provider value={ {formData, setFormData, todoList, setTodoList, loading, setLoading, isEdit, setIsEdit, modalIsOpen, setModalIsOpen, currentTodo, setCurrentTodo, formErrors, setFormErrors} }>
        {children}
        </GlobalContext.Provider>
    );
}