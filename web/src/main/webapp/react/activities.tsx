import React from "react"

type Activity = {
    id: number,
    dateStart: String,
    dateEnd: String,
    volume: number
}

export interface ActivitiesState {
    activities: Activity[]
}

export interface ActivitiesProps {
    userId: number,
    accessId: number,
    handleClick(contentType: string, id: number): void
}
export default class Activities extends React.Component<ActivitiesProps> {
    state: ActivitiesState = {
        activities: []
    }

    constructor(props: ActivitiesProps) {
        super(props);
    }

    componentDidMount() {
        const url = "ajax/activity?authorityId=" + this.props.accessId;
        fetch(url)
            .then(result => result.json())
            .then(result => {
                this.setState({
                    activities: result
                })
            });
    }
    render() {
        let activities = this.state.activities;
        const result = activities.map( (activity: Activity) => {
            return (
                <tbody>
                    <tr>
                        <td>{activity.id}</td>
                        <td>{activity.dateStart}</td>
                        <td>{activity.dateEnd}</td>
                        <td>{activity.volume}</td>
                    </tr>
                </tbody>
            );
        });
        return (
            <div className="container">
                <button
                    className="btn btn-outline-secondary mb-2"
                    onClick={() => {
                        this.props.handleClick("Accesses", this.props.userId);
                    }}
                >
                    Назад
                </button>
                <table className="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">dateStart</th>
                            <th scope="col">dateEnd</th>
                            <th scope="col">volume</th>
                        </tr>
                    </thead>
                    {result}
                </table>
            </div>
        );
    }
}
