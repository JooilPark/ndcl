import React from 'react'
import './TodoListTemplet.css'

const TodoListTemplet = ({form , children}) =>{
    return (
        <main className="todo-list-templete">
            <div className="title">
                오늘 할일
            </div>
            <section className="form-wrapper">
                {form}
            </section>
            <section className="todos-wrapper">
                {children}
            </section>
        </main>
    );
}

export default TodoListTemplet;