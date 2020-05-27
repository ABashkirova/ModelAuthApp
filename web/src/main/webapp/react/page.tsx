import React from "react"
import ReactDOM from "react-dom"
import Table from "./table"
import Form from "./form"

interface PageState {
    contentType: string,
    userId: number,
    accessId: number
}

class Page extends React.Component<PageState, {}> {
    state: PageState = {
        contentType: "",
        userId: 0,
        accessId: 0
    }
    constructor(props: PageState) {
        super(props);
        this.state = props
        this.handleFormSubmit = this.handleFormSubmit.bind(this);
    }
    handleFormSubmit(userId: number, accessId: number) {
        console.log("function handleFormSubmit");
        let ct = "Activities";
        this.setState({
            contentType: ct,
            userId: userId,
            accessId: accessId
        });
        console.log("handleSubmitForm userId: " + this.state.userId);
        console.log("handleSubmitForm accessId: " + this.state.accessId);
        console.log("in Page.handleFormSubmit Page.state.contentType: " + this.state.contentType);
    }

    render() {
        console.log("in Page render userId: " + this.state.userId);
        console.log("in Page render accessId: " + this.state.accessId);
        console.log("in Page render contentType: " + this.state.contentType);
        return (
            <div>
                <nav className="navbar navbar-dark bg-secondary mb-3">
                    <span className="navbar-brand">Model Auth App</span>
                </nav>
                <Table
                    contentType={this.props.contentType}
                    userId={this.props.userId}
                    accessId={this.props.accessId}
                />
                <Form refreshActivities={this.handleFormSubmit} />
            </div>
        );
    }
}
// @ts-ignore
ReactDOM.render(<Page />, document.getElementById("root"));
