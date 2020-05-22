class Table extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            contentType: this.props.contentType,
            userId: this.props.userId,
            accessId: this.props.accessId
        };
        this.handleUserClick = this.handleUserClick.bind(this)
        this.handleAccessClick = this.handleAccessClick.bind(this)
        this.handleActivityClick = this.handleActivityClick.bind(this)
    }

    componentWillReceiveProps(nextProps) {
        // This will erase any local state updates!
        // Do not do this.
        console.log(nextProps)
        this.setState({
            contentType: nextProps.contentType,
            userId: nextProps.userId,
            accessId: nextProps.accessId
        });
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
//        console.log("in Table render state.userId: "+this.state.userId)
//        console.log("in Table render props.userId: "+this.props.userId)
//        console.log("in Table render state.contentType: "+this.state.contentType)
//        console.log("in Table render props.contentType: "+this.props.contentType)
        const ct = this.state.contentType;
        if (ct === 'Activities') {
            return <Activities handleClick={this.handleActivityClick} userId={this.state.userId} accessId={this.state.accessId}/>
        } else if (ct === 'Accesses') {
            return <Accesses handleClick={this.handleAccessClick} userId={this.state.userId}/>
        } else {
            return <Users handleClick={this.handleUserClick}/>
        }
    }
}