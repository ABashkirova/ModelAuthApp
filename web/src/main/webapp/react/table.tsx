import React from "react"
import Activities from "./activities"
import Accesses from "./accesses"
import Users from "./users"

export interface TableState {
    contentType: string,
    userId: number,
    accessId: number
};

export default class Table extends React.Component<TableState> {
    state: TableState = {
        contentType: "",
        userId: 0,
        accessId: 0
    }

    constructor(props: TableState) {
        super(props);
        this.state.contentType = props.contentType
        this.state.userId = props.userId
        this.state.accessId = props.accessId
        
        this.handleUserClick = this.handleUserClick.bind(this);
        this.handleAccessClick = this.handleAccessClick.bind(this);
        this.handleActivityClick = this.handleActivityClick.bind(this);
    }

    componentWillReceiveProps(nextProps: TableState) {
        // This will erase any local state updates!
        // Do not do this.
        console.log(nextProps);
        this.setState({
            contentType: nextProps.contentType,
            userId: nextProps.userId,
            accessId: nextProps.accessId
        });
    }

    handleUserClick(type: string, userId: number) {
        this.setState({ contentType: type, userId: userId });
    }

    handleAccessClick(type: string, accessId: number) {
        this.setState({ contentType: type, accessId: accessId });
    }

    //кнопка назад вернет доступ с последним сохранённым userId
    handleActivityClick(type: string) {
        this.setState({ contentType: type });
    }

    render() {
        console.log("in Table render state.userId: "+this.state.userId)
        console.log("in Table render state.contentType: "+this.state.contentType)
        const ct = this.state.contentType;
        if (ct === "Activities") {
            return (
                <Activities handleClick={this.handleActivityClick} userId={this.state.userId} accessId={this.state.accessId} />
            );
        } else if (ct === "Accesses") {
            return <Accesses handleClick={this.handleAccessClick} userId={this.state.userId} />;
        } else {
            return <Users handleClick={this.handleUserClick} />;
        }
    }
}
