import { Link } from "react-router-dom";
import { GlobalContext } from "../context";
import { useContext } from "react";

export default function Header({ img }) {
    const { setFormData } = useContext(GlobalContext)

    function handleClick() {
        setFormData({ name: "", status: "", description: "" })
    }

    return (
        <div className="flex bg-blue-500 text-white p-4 mb-4 items-center">
            <h3 className="">
                <Link onClick={handleClick} to={"/"}>
                    <img src={img} alt="Website logo" width={"200px"} height={"50px"}/>
                </Link>
            </h3>
            <ul className="flex gap-[20px] w-full justify-end">
                <Link onClick={handleClick} to={"/"}>
                    <li>Home</li>
                </Link>
                <Link onClick={handleClick} to={"/"}>
                    <li>Register</li>
                </Link>
                <Link onClick={handleClick} to={"/"}>
                    <li>Login</li>
                </Link>
            </ul>
        </div>
    )
}