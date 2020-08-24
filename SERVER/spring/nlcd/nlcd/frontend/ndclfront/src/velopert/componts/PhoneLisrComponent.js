import React , {Component}from 'react';
import PhoneInfo from './PhoneInfo';

class PhonrListComponent extends Component{
    static defaultProps = {
        data : [] , 
        onRemove: ()=> console.warn('onRemove not defind'),
        onUpdate : () => console.warn('onUPdate not defind')
    };
    shouldComponentUpdate(nextProps , nextState){
        return nextProps.data!== this.props.data
    }   

    render(){
        console.log('render PhoneInfoList');
        const { data  , onRemove , onUpdate}  = this.props; 
        const list = data.map(
            info => (
                <PhoneInfo key = {info.id} info = {info} onRemove = {onRemove}  onUpdate = {onUpdate}/>
            )

        )
           
        return (
        <div>{list}</div>
        )
        
    }
}

export default PhonrListComponent
