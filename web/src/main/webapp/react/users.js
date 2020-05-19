class Users extends React.Component {

  constructor(props) {
      super(props);
      this.state = {
        users: []
      };
  }


  componentDidMount() {
    const url = 'ajax/user'

    fetch(url)
      .then(result => result.json())
      .then(result => {
        this.setState({
          users: result
        })
      })
  }


  render() {
    const { users } = this.state

    const result = users.map(user => {
        return (
            //событие onclick обрабатывает родительский метод, который передается через props
            <tr onClick={() => {this.props.handleClick('Accesses', user.id)}} key={user.id}>
                <td>{user.id}</td>
                <td>{user.login}</td>
            </tr>
        )
    })

    return (
    <div className="container">
        <table className="table table-bordered">{result}</table>
    </div>
    )
  }
}