class Table extends React.Component {
    constructor(props) {
        super(props);
        this.state = {contentType: 'Users', id: null};
        this.handleClick = this.handleClick.bind(this)
    }

    handleClick(type, id) {
        this.setState({contentType: type, id: id})
    }

    render() {
        const ct = this.state.contentType;
        if (ct === 'Activities') {
            return <Activities handleClick={this.handleClick} accessId={this.state.id}/>
        } else if (ct === 'Accesses') {
            return <Accesses handleClick={this.handleClick} userId={this.state.id}/>
        } else {
            return <Users handleClick={this.handleClick}/>
        }
    }
}
