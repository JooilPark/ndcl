import React , {Component}from 'react';
class Phoneform extends Component{
    state = {
        name:'',
        phone : ''
    }
    handleChange  = (e)=>{
        this.setState({
            [e.target.name] : e.target.value
            

        })
    }
    handleSumit = (e)=>{
        e.preventDefault()
        this.props.onCreate(this.state)
        this.setState({
            name : '',
            phone : ''
        })
    }
    render(){
        return (
            <form onSubmit={this.handleSumit}>
                <input placeholder="이름"
                        value = {this.state.name}
                        onChange = {this.handleChange}
                        name = "name"
                />
                <input
                    placeholder="number"
                    value = {this.state.phone}
                    onChange = {this.handleChange}
                    name = "phone"
                />
                    <div>
                        {this.state.name}   {this.state.phone}
                    </div>
                
                    <button type='submit'>regiest</button>
            </form>
        );
    }
}


export default Phoneform