class Accesses extends React.Component {
    render() {
        return (
        <div>
            <button className="btn btn-light" onClick={() => {this.props.handleClick('Users')}}>Назад</button>
            <p>id: {this.props.userId}</p>
        </div>
        )
    }
}