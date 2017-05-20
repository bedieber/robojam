package robojam;

public class Transform
    {
        private Vector3 _rotation;
        private Vector3 _position;

        /// <summary>
        /// Gets or sets the rotation of an object
        /// </summary>
        /// <value>
        /// The Rotation.
        /// </value>
        public Vector3 getRotation()
        {
            return _rotation; }
			
		public void setRotation(Vector3 value) { _rotation = value; }
        

        /// <summary>
        /// Gets or sets the position of an object
        /// </summary>
        /// <value>
        /// The Position.
        /// </value>
        public Vector3 getPosition()
             { return _position; }
            public void setPosition(Vector3 value) { _position = value; }
        

        /// <summary>
        /// Creates a new Instance of the <see cref="Transform"/> class.
        /// </summary>
        public Transform()
        {
            _rotation = new Vector3();
            _position = new Vector3();
        }

        /// <summary>
        /// Creates a new Instance of the <see cref="Transform"/> class.
        /// </summary>
        public Transform(Vector3 position, Vector3 rotation)
        {
            _rotation = new Vector3(rotation.getX(), rotation.getY(), rotation.getZ());
            _position = new Vector3(position.getX(), position.getY(), position.getZ());
        }

        /// <summary>
        /// Returns a string representation <see cref="Transform"/> class.
        /// </summary>
        public String toString()
        {
            return "Position:" + getPosition().toString() + "; Rotation:" + getRotation().toString();
        }
    }	