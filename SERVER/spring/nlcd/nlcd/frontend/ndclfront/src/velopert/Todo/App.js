import React , {Component} from 'react';
import TodoListTemplet from './TodoListTemplet';
import Form from './form'
import TodoItemlist from './TodoItemList';
//https://velopert.com/3480

 class App extends Component{
      render(){
          return (
            <TodoListTemplet form={<Form/>}><TodoItemlist/></TodoListTemplet>
          );
      }
 }
 export default App;
