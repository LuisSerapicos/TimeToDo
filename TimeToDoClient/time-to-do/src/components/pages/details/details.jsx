import { useContext } from "react";
import Modal from "react-modal"
import { GlobalContext } from "../../context";

export default function Details() {
    const { modalIsOpen, setModalIsOpen, currentTodo } = useContext(GlobalContext)

    function closeModal() {
        setModalIsOpen(false);
    }

    return (
        <div>
            <Modal isOpen={modalIsOpen} onRequestClose={closeModal} contentLabel="Task Details" style={{overlay: {position: "fixed", backgroundColor: "transparent"}, 
            content: {display: "flex", flexDirection: "column", justifyContent: "start", alignItems: "center", position: "absolute", top: "10vh", left: "50%", transform: "translate(-50%, 0%)", maxHeight: "50vh", maxWidth: "700px", backgroundColor: "#5484d1"}}}>
                <button className="self-end text-white mr-3" onClick={closeModal}>X</button>
                <h1 className="mt-5 mb-10 text-white text-3xl font-bold">Task Details</h1>
                <div className="text-left">
                    <p className="mb-5 text-white"><span className="font-bold">Task: </span>{currentTodo ? currentTodo.name : ''}</p>
                    <p className="mb-5 text-white"><span className="font-bold">Status: </span>{currentTodo ? currentTodo.status : ''}</p>
                    <p className="mb-5 text-white"><span className="font-bold">Description: </span> {currentTodo ? currentTodo.description : ''}</p>
                </div>
            </Modal>
        </div>
    )
}