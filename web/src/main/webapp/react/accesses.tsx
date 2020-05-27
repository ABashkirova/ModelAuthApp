import React from "react"
import { User } from "./users"

type Access = {
    id: number,
    user: User,
    resource: string,
    role: string
}

export interface AccessesState {
    accesses: Access[]
}

export interface AccessProps {
    userId: number,
    handleClick(contentType:string, id: number): void
}

export default class Accesses extends React.Component<AccessProps> {
    state: AccessesState = {
        accesses: []
    }

    constructor(props: AccessProps) {
        super(props);
    }

    componentDidMount() {
        let userId = this.props.userId
        const url = "ajax/authority?userId=" + userId;
        fetch(url)
            .then(result => result.json())
            .then(result => {
                this.setState({
                    accesses: result
                })
            });
    }
    render() {
        let accesses = this.state.accesses;
        const result = accesses.map(access => {
            return (
                <tbody>
                    <tr
                        role="button"
                        onClick={() => {
                            this.props.handleClick("Activities", access.id);
                        }}
                        key={access.id}
                    >
                        <td>{access.id}</td>
                        <td>{access.user.login}</td>
                        <td>{access.resource}</td>
                        <td>{access.role}</td>
                    </tr>
                </tbody>
            );
        });
        return (
            <div className="container">
                <button
                    className="btn btn-outline-secondary mb-2"
                    onClick={() => {
                        this.props.handleClick("Users", 0);
                    }}
                >
                    Назад
                </button>
                <table className="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">login</th>
                            <th scope="col">resource</th>
                            <th scope="col">role</th>
                        </tr>
                    </thead>
                    {result}
                </table>
            </div>
        );
    }
}
