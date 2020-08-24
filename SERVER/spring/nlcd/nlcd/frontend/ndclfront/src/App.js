import React , {Component} from 'react';


class App extends Component{
state ={
  hello:"Hello App",
  count:0

}

 handleChange =()=> {
   this.setState({
     hello:this.state.hello  + (this.state.count +1)
   })
 }
  render(){
  return  (
    <div className = "APP">
      <div className="props">
        {}
        <span>{this.props.message} </span>
      </div>

      <h3>state</h3>
      <div>
      <div>{this.state.hello}</div>
        <button  onClick={this.handleChange}>Click Me</button>
      </div>

      <h3>APP2</h3>
      <div className="inside-app-props">
        <InsideApp  hello={this.state.hello} 
        handleChange={this.handleChange}/>
      </div>
    </div>
  )
  }
}

class InsideApp extends Component{
  render(){
    return (
      <div>
        {this.props.hello}
        <button onClick={this.props.handleChange}> Clickme2 </button>
      </div>
    );
  }

}
export default App;
