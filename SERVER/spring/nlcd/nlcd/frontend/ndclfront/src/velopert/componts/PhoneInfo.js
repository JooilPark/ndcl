import React, { Component } from 'react'

export class PhoneInfo extends Component {
    static dafaultProps = {
        info : {
            name : 'name',
            phobe : '010-0000-0000',
            id : 0
        }
    }
    state = {
        editing  : false,
        name : '',
        phone : ''
    }
    handleRemove = (data) =>{
        const {info , onRemove} = this.props
        onRemove(info.id)
    }

    handleToggleEdit = () =>{
        const {editing} = this.state
        this.setState({editing : !editing});
        console.log(editing)
    }
    handleChange = (e) =>{
        const {name , value} = e.target
        this.setState({[name] : value})
    }
    componentUpdate(prevProps , prevState){
        const {info , onUpdate} = this.props
        if(!prevState.editing && this.state.editing){
            this.setState({
                name : info.name,
                phone : info.phone
            })
        }
        if(prevState.editing && !this.state.editing){
            onUpdate(info.id , {
                name : this.state.name,
                phone : this.state.phone
            })
        }
    }
    render() {
        const style= {
            border  : '1px solid black',
            padding : '8px',
            margin : '8px'
        }
        const {
             editing
        } = this.state
        if(editing){
            return (
                <div style={style}>
                    <div>
                        <input value={this.state.name} name = "name" placeholder="name" onChange = {this.handleChange}/>

                    </div>
                    <div>
                        <input value={this.state.phone} name="phone" placeholder="phone" onChange = {this.handleChange}/>
                    </div>
                    <button onClick = {this.handleToggleEdit}>update</button>
                    <button onClick = {this.handleRemove}>delete</button>
                </div>
            )
        }
        const {
            name , phone , id 
        } = this.props.info;
        return (
            <div style = {style}>
                <div><b>{name}</b></div>
                <div>{phone}</div>
                <button onClick = {this.handleToggleEdit}>edit </button>
                <button onClick = {this.handleRemove}>delete</button>
            </div>
        )
    }
}



export default PhoneInfo
