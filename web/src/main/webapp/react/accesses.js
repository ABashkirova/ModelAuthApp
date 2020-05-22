class Accesses extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            accesses: []
        };
    }

    componentDidMount() {
        const url = 'ajax/authority?userId=' + this.props.userId

        fetch(url)
            .then(result => result.json())
            .then(result => {
                this.setState({
                    accesses: result
                })
            })
    }

    render() {
        const {accesses} = this.state

        const result = accesses.map(access => {
            return (
                // событие onclick обрабатывает родительский метод,
                // который передается через props
                <tbody>
                <tr role="button" onClick={() => {
                    this.props.handleClick('Activities', access.id)
                }} key={access.id}>
                    <td>{access.id}</td>
                    <td>{access.resource}</td>
                    <td>{access.role}</td>
                </tr>
                </tbody>
            )
        })

        return (
            <div className="container">
                <button className="btn btn-outline-secondary mb-2" onClick={() => {
                    this.props.handleClick('Users')
                }}>Назад
                </button>
                <table className="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th scope="col">id</th>
                        <th scope="col">resource</th>
                        <th scope="col">role</th>
                    </tr>
                    </thead>
                    {result}
                </table>
            </div>
        )
    }
}
