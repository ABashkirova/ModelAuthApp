class Accesses extends React.Component {
  constructor(props) {
      super(props);
      this.state = {
        accesses: []
      };
  }


  componentDidMount() {
    const url = 'ajax/authority?userId='+this.props.userId

    fetch(url)
      .then(result => result.json())
      .then(result => {
        this.setState({
          accesses: result
        })
      })
    }

    render() {
        const { accesses } = this.state

        const result = accesses.map(access => {
            return (
                //событие onclick обрабатывает родительский метод, который передается через props
                <tr onClick={() => {this.props.handleClick('Activity', access.id)}} key={access.id}>
                    <td>{access.id}</td>
                    <td>{access.userId}</td>
                    <td>{access.resource}</td>
                    <td>{access.role}</td>
                </tr>
            )
        })
        return (
        <div className="container">
            <button className="btn btn-outline-primary" onClick={() => {this.props.handleClick('Users')}}>Назад</button>
            <table className="table">
                {result}
            </table>
        </div>
        )
    }
}