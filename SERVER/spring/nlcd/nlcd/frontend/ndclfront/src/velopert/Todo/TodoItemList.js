import React , {Component} from 'react';
import TodoItem from './TodoItem'
class TodoListItem extends Component{
    render(){
        const {todos , onToggle , onRemove} = this.props
        return (
            <div>
                <TodoItem>1</TodoItem>
                <TodoItem>2</TodoItem>
                <TodoItem>3</TodoItem>
                
            </div>
        )
    }
}
export default TodoListItem