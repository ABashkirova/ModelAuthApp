import React from "react"

export type User = {
    id: number, 
    login: String
}
export interface UsersState {
    users: User[]
}

export interface UserProps {
    handleClick(contentType: string, id: number): void
}

export default class Users extends React.Component<UserProps> {
    state: UsersState = {
        users: []
    }

    constructor(props: UserProps) {
        super(props);
        console.log("in user constuctor ", props)
    }

    componentDidMount() {
        const url = "ajax/user";
        fetch(url)
            .then(result => result.json())
            .then(result => {
                this.setState({
                    users: result
                })
            });
    }

    render() {
        let users = this.state.users;
        const result = users.map( (user: User) => {
            console.log("in User ", result)
            return (
                <tbody>
                <tr role="button" onClick={() => {
                    this.props.handleClick('Accesses', user.id)
                }} key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.login}</td>
                </tr>
                </tbody>
            );
        });
        return (
            <div className="container">
                <table className="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">login</th>
                        </tr>
                    </thead>
                    {result}
                </table>
            </div>
        );
    }
}
