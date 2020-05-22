class Page extends React.Component {

    constructor(props){
        super(props)
        this.state = {
             contentType: null,
             userId: null,
             accessId: null
        };
        this.handleFormSubmit = this.handleFormSubmit.bind(this)
    }

    handleFormSubmit(userId, accessId){
//        console.log("function handleFormSubmit")
        let ct = 'Activities'
        this.setState({
            contentType: ct,
            userId: userId,
            accessId: accessId
        });
//        console.log("handleSubmitForm userId: "+this.state.userId)
//        console.log("handleSubmitForm accessId: "+this.state.accessId)
//        console.log("in Page.handleFormSubmit Page.state.contentType: "+this.state.contentType)

    }

    render() {
//        console.log("in Page render userId: "+this.state.userId)
//        console.log("in Page render accessId: "+this.state.accessId)
//        console.log("in Page render contentType: "+this.state.contentType)
        return (
            <div>
                <nav className="navbar navbar-dark bg-secondary mb-3">
                    <span className="navbar-brand">Model Auth App</span>
                </nav>
                <Table contentType={this.state.contentType} userId={this.state.userId} accessId={this.state.accessId}/>
                <Form refreshActivities={this.handleFormSubmit}/>
            </div>
        )
    }
}

ReactDOM.render(
    <Page/>,
    document.getElementById('root')
);
