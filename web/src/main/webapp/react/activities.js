class Activities extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            activities: []
        };
    }

    componentDidMount() {
        const url = 'ajax/activity?authorityId=' + this.props.accessId

        fetch(url)
            .then(result => result.json())
            .then(result => {
                this.setState({
                    activities: result
                })
            })
    }

    render() {
        const {activities} = this.state

        const result = activities.map(activity => {
            return (
                <tbody>
                <tr>
                    <td>{activity.id}</td>
                    <td>{activity.dateStart}</td>
                    <td>{activity.dateEnd}</td>
                    <td>{activity.volume}</td>
                </tr>
                </tbody>
            )
        })

        return (
            <div className="container">
                <button className="btn btn-outline-secondary mb-2" onClick={() => {
                    this.props.handleClick('Accesses')
                }}>Назад
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
        )
    }
}
