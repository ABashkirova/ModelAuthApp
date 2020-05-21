class Table extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            contentType: 'Users',
            userId: null,
            accessId: null
        };
        this.handleUserClick = this.handleUserClick.bind(this)
        this.handleAccessClick = this.handleAccessClick.bind(this)
        this.handleActivityClick = this.handleActivityClick.bind(this)
    }

    handleUserClick(type, userId) {
        this.setState({contentType: type, userId: userId})
    }

    handleAccessClick(type, accessId) {
        this.setState({contentType: type, accessId: accessId})
    }

    //кнопка назад вернет доступ с последним сохранённым userId
    handleActivityClick(type) {
        this.setState({contentType: type})
    }

    render() {
        const ct = this.state.contentType;
        if (ct === 'Activities') {
            return <Activities handleClick={this.handleActivityClick} accessId={this.state.accessId}/>
        } else if (ct === 'Accesses') {
            return <Accesses handleClick={this.handleAccessClick} userId={this.state.userId}/>
        } else {
            return <Users handleClick={this.handleUserClick}/>
        }
    }
}