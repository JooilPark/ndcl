import React , {Component}from 'react';
import Phoneform from './Phoneform';
import PhonrListComponent from './PhoneLisrComponent'
class App extends Component{
    id = 2
    state = {
        information : [
            {id : 0 ,
            name : 'name1',
            phone : '010-0000-0001'
        },
        {
            id : 1 , 
            name : 'name2',
            phone : '010-0000-0002'
        }

        ]
    }
    handleChange = (e)=>{
        this.setState({
            keyword : e.target.value
        }) 
        console.log(e.target.value)
    }
    handleCreate= (data) =>{
        const {information} = this.state
        this.setState(
            {
                information : information.concat({id : this.id++, ...data})
            }
        )
        console.log(data)
    }
    handleRemove = (data)=>{
        const {information}  = this.state
        this.setState({
            information : information.filter(info => info.id !== data)
        }) 
    }
    handleUpdate = (id , data) =>{
        const {information} = this.state
        
        this.setState({
            information : information.map(
                info => id === info.id ? {...info , ...data} :info 
            )    
        })
        console.log(data)
    }
    render(){
        const {information , keyword} = this.state
        const filteredlist = information.filter(
            info => info.name.indexOf(keyword) !== -1
        )
        return (<div>
            <Phoneform onCreate={this.handleCreate}/>
            <p>
                <input placeholder="검색어" onChange={this.handleChange} value={keyword}/>
            </p>
            <PhonrListComponent data = {filteredlist} onRemove = {this.handleRemove} onUpdate = {this.handleUpdate}/>
        </div>)
    }
}
export default App